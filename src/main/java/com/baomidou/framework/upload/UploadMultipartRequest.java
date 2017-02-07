/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.framework.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.framework.common.SwConstants;
import com.baomidou.framework.upload.multipart.FilePart;
import com.baomidou.framework.upload.multipart.FileRenamePolicy;
import com.baomidou.framework.upload.multipart.MacBinaryDecoderOutputStream;
import com.baomidou.framework.upload.multipart.MultipartParser;
import com.baomidou.framework.upload.multipart.ParamPart;
import com.baomidou.framework.upload.multipart.Part;

/**
 * <p>
 * cos 文件上传请求
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-21
 */
public class UploadMultipartRequest {

	protected Logger logger = LoggerFactory.getLogger(UploadMultipartRequest.class);

	private static final int DEFAULT_MAX_POST_SIZE = 1024 * 1024; // 1 Meg

	@SuppressWarnings("rawtypes")
	protected Hashtable files = new Hashtable(); // name - CosFileInfo

	private HttpServletRequest request;

	private String saveDirectory;

	private int maxPostSize;

	private FileRenamePolicy fileRenamePolicy;

	/*
	 * <p>
	 * 头文件 + 后缀  = 验证
	 * </p>
	 * <p>
	 * 例如： ffd8ff.jpg;000000.mp4;255044.pdf
	 * </p>
	 */
	private String fileHeaderExts = null;

	private String charset = SwConstants.UTF_8;


	protected UploadMultipartRequest() {
		/* 保护 */
	}


	public UploadMultipartRequest( HttpServletRequest request, String saveDirectory ) {
		this(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
	}


	public UploadMultipartRequest( HttpServletRequest request, String saveDirectory, int maxPostSize ) {
		this.request = request;
		this.saveDirectory = saveDirectory;
		this.maxPostSize = maxPostSize;
	}


	@SuppressWarnings("unchecked")
	public void upload() throws IOException {
		// Sanity check values
		if ( request == null ) {
			throw new IllegalArgumentException("request cannot be null");
		}
		if ( saveDirectory == null ) {
			throw new IllegalArgumentException("saveDirectory cannot be null");
		}
		if ( maxPostSize <= 0 ) {
			throw new IllegalArgumentException("maxPostSize must be positive");
		}

		// Save the dir
		File dir = new File(saveDirectory);

		// Check saveDirectory is truly a directory
		if ( !dir.isDirectory() ) {
			throw new IllegalArgumentException("Not a directory: " + saveDirectory);
		}

		// Check saveDirectory is writable
		if ( !dir.canWrite() ) {
			throw new IllegalArgumentException("Not writable: " + saveDirectory);
		}

		// Parse the incoming multipart, storing files in the dir provided, 
		// and populate the meta objects which describe what we found
		MultipartParser parser = new MultipartParser(request, maxPostSize, true, true, getCharset());

		//读取上传参数
		HashMap<String, String> paramParts = new HashMap<String, String>();

		Part part;
		while ( (part = parser.readNextPart()) != null ) {
			String name = part.getName();
			if ( name == null ) {
				throw new IOException("Malformed input: parameter name missing (known Opera 7 bug)");
			}
			if ( part.isFile() ) {
				// It's a file part
				FilePart filePart = (FilePart) part;
				String fileName = filePart.getFileName();
				if ( fileName != null ) {
					//filePart.setRenamePolicy(policy); // null policy is OK
					// The part actually contained a file
					UploadFile cfi = writeTo(dir, fileName, getFileRenamePolicy(), filePart);
					cfi.setDir(dir.toString());
					cfi.setOriginal(fileName);
					cfi.setParamParts(paramParts);
					files.put(name, cfi);
				} else {
					// The field did not contain a file
					files.put(name, new UploadFile());
				}
			} else if ( part.isParam() ) {
				ParamPart paramPart = (ParamPart) part;
				paramParts.put(paramPart.getName(), paramPart.getStringValue());
			}
		}
	}


	/**
	 * 输出文件
	 */
	private UploadFile writeTo( File fileOrDirectory, String fileName, FileRenamePolicy policy, FilePart filePart )
		throws IOException {
		OutputStream fileOut = null;
		UploadFile cf = new UploadFile();
		try {
			// Only do something if this part contains a file
			if ( fileName != null ) {
				BufferedInputStream in = new BufferedInputStream(filePart.getInputStream());
				cf.setType(filePart.getContentType());
				/**
				 * 设置了扩展名
				 * 
				 * <p>判断头文件</p>
				 */
				if ( StringUtils.isNotBlank(fileHeaderExts) ) {
					try {
						/**
						 * 读取文件头 3 个字节判断文件类型
						 */
						byte[] data = new byte[3];
						in.mark(3);
						in.read(data, 0, data.length);
						in.reset();
						String fileExt = readFileExt(data, fileName);
						if ( fileExt != null ) {
							cf.setSuffix(fileExt);
						} else {
							cf.setUploadCode(UploadCode.ILLEGAL_EXT);
							logger.debug(" upload fileType is null.");
							return cf;
						}
					} catch ( Exception e ) {
						logger.debug("upload file error. ", e);
						cf.setUploadCode(UploadCode.EXCEPTION);
						return cf;
					}
				} else {
					cf.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
				}

				// Check if user supplied directory
				File file;
				if ( fileOrDirectory.isDirectory() ) {
					// Write it to that dir the user supplied, 
					// with the filename it arrived with
					file = new File(fileOrDirectory, fileName);
				} else {
					// Write it to the file the user supplied,
					// ignoring the filename it arrived with
					file = fileOrDirectory;
				}

				if ( policy instanceof UploadFileRenamePolicy ) {
					((UploadFileRenamePolicy) policy).setSuffix(cf.getSuffix());
				}

				if ( policy != null ) {
					file = policy.rename(file);
					fileName = file.getName();
					cf.setFilename(fileName);
				}

				fileOut = new BufferedOutputStream(new FileOutputStream(file));
				cf.setSize(write(fileOut, filePart.getContentType(), in));
			}
		} catch ( Exception e ) {
			logger.debug("upload file write error. ", e);
			cf.setUploadCode(UploadCode.EXCEPTION);
		} finally {
			if ( fileOut != null ) fileOut.close();
		}
		return cf;
	}


	/**
	 * <p>
	 * 读取文件扩展名
	 * </p>
	 * 
	 * @param data
	 * 				文件头 3 个字节
	 * @param fileName
	 * 				文件名
	 * @return
	 * @throws Exception
	 */
	private String readFileExt( byte[] data, String fileName ) throws Exception {
		String fileExt = fileName.substring(fileName.lastIndexOf("."));
		StringBuffer fe = new StringBuffer();
		fe.append(UploadFileHeader.bytesToHexString(data));
		fe.append(fileExt);
		if ( fileHeaderExts.contains(fe.toString()) ) {
			return fileExt;
		}
		return null;
	}


	private long write( OutputStream out, String contentType, InputStream in ) throws IOException {
		// decode macbinary if this was sent
		if ( contentType.equals("application/x-macbinary") ) {
			out = new MacBinaryDecoderOutputStream(out);
		}
		long size = 0;
		int read;
		byte[] buf = new byte[8 * 1024];
		while ( (read = in.read(buf)) != -1 ) {
			out.write(buf, 0, read);
			size += read;
		}
		return size;
	}


	public UploadMultipartRequest( ServletRequest request, String saveDirectory ) throws IOException {
		this((HttpServletRequest) request, saveDirectory);
	}


	public UploadMultipartRequest( ServletRequest request, String saveDirectory, int maxPostSize ) throws IOException {
		this((HttpServletRequest) request, saveDirectory, maxPostSize);
	}


	@SuppressWarnings("rawtypes")
	public Enumeration getFileNames() {
		return files.keys();
	}


	public UploadFile getUploadFile( String name ) {
		try {
			// may be null
			return (UploadFile) files.get(name);
		} catch ( Exception e ) {
			return null;
		}
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public String getSaveDirectory() {
		return saveDirectory;
	}


	public FileRenamePolicy getFileRenamePolicy() {
		if ( fileRenamePolicy == null ) {
			return new UploadFileRenamePolicy();
		}
		return fileRenamePolicy;
	}


	public void setFileRenamePolicy( FileRenamePolicy fileRenamePolicy ) {
		this.fileRenamePolicy = fileRenamePolicy;
	}


	public String getFileHeaderExts() {
		return fileHeaderExts;
	}


	public void setFileHeaderExts( String fileHeaderExts ) {
		this.fileHeaderExts = fileHeaderExts;
	}


	public String getCharset() {
		return charset;
	}


	public void setCharset( String charset ) {
		this.charset = charset;
	}

}
