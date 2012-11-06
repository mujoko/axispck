/**
 * Copyright (c) {2011} {meter@rbtsb.com} {
 * individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Private License v1.0
 * which accompanies this distribution, and is available at
 * http://www.rbtsb.com
 */
package com.openet.axis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * @author Sravan.
 */
public final class FileUtil {


	/**
	 * Constructor for file utility class.
	 */
	private FileUtil() {
	}

	/**
	 * The default escape character to use if none is supplied to the
	 * constructor.
	 */
	public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
	/** The escape constant to use when you wish to suppress all escaping. */
	public static final char NO_ESCAPE_CHARACTER = '\u0000';



	/**
	 * @param directoryLocation
	 *            directory Location
	 * @return .
	 */
	public static boolean isEmpty(String string) {
		if (string ==null){
			return true;
		}

		if (string.trim().equals("")||string.trim().equals("null")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param directoryLocation
	 *            directory Location
	 * @return .
	 */
	public static String getFileNameFromDirectory(String directoryLocation) {
		String fileName = null;
		File dir = new File(directoryLocation);
		String[] children = dir.list();
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				fileName = children[i];
			}
		}
		return fileName;
	}

	/**
	 * @param directoryLocation
	 *            Target Directory.
	 * @return Array of file Name.
	 */
	public static String[] getFileNameArrayFromDirectory(
			String directoryLocation) {
		String[] children = null;
		try {
			File dir = new File(directoryLocation);
			children = dir.list();
			if (children == null) {
				children = new String[0];
			}

		} catch (Exception e) {
			e.printStackTrace();
			children = new String[0];
		}
		return children;
	}

	/**
	 * @param value
	 *            some string.
	 * @param start
	 *            index start.
	 * @param end
	 *            index end.
	 * @return string.
	 */
	public static String afterTrim(String value, int start, int end) {
		String subString = null;
		try {
			StringBuffer sb = new StringBuffer(value);
			subString = sb.substring(start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return subString;
		}
		return subString;
	}

	/**
	 * @param src
	 *            source.
	 * @param dest
	 *            destination.
	 */
	// move files from src to dest and delete from dest
	public static void moveDeleteDirectoryFiles(File src, File dest) {
		try {
			copyDirectory(src, dest);
			delete(src);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Move File by invoke Unix command.
	 */
	public static void moveFileUnix(final String srcFullPath,
			final String destFullPath) {
		try {
			Process process = Runtime.getRuntime().exec(
					"mv " + srcFullPath + "  " + destFullPath);
			InputStream input = process.getInputStream();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Move File by invoke Unix command.
	 */
	public static void moveFileUnix(final File src, final File dest) {
		try {
			if (src.exists() && !dest.exists()) {
				Process process = Runtime.getRuntime().exec(
						"mv " + src.getAbsolutePath() + "  "
								+ dest.getAbsolutePath());
				@SuppressWarnings("unused")
				InputStream input = process.getInputStream();
			}
		} catch (IOException e) {
		}

	}

	/**
	 * Move File by invoke Unix command.
	 */
	public static void deleteFileUnix(final File src) {
		try {
			Process process = Runtime.getRuntime().exec(
					"rm  " + src.getAbsolutePath());
			InputStream input = process.getInputStream();
			input.close();

		} catch (IOException e) {
		}

	}

	/**
	 * @param src
	 *            source.
	 * @param dest
	 *            destination.
	 */
	// move files from src to dest and delete from dest
	public static void moveDeleteFiles(final File src, final File dest) {
		if (!src.getParentFile().exists()) {
			src.mkdirs();
		}
		if (!dest.getParentFile().exists()) {
			dest.mkdirs();
		}
		if (src.renameTo(dest)) {
		} else {
			moveFileUnix(src, dest);
		}
	}

	/**
	 * @param src
	 *            source.
	 * @param dest
	 *            destination.
	 */
	// move files from src to dest and delete from dest
	public static void moveDeleteFiles2(final File src, final File dest) {
		try {
			copyFile(src, dest);
			delete(src);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param src
	 *            source.
	 * @param dest
	 *            destination.
	 */
	public static void copyDirectory(final File sourceDir, final File destDir)
			throws IOException {
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		File[] children = sourceDir.listFiles();
		for (File sourceChild : children) {
			String name = sourceChild.getName();
			File destChild = new File(destDir, name);
			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, destChild);

			} else {
				copyFile(sourceChild, destChild);

			}
		}
	}

	/**
	 * @param source
	 *            source.
	 * @param dest
	 *            destination.
	 * @throws Exception .
	 */
	public static void copyFile(final File source, final File dest)
			throws IOException {
		if (!dest.getParentFile().exists()) {
			String dir = dest.getParentFile().toString();
			new File(dir).mkdir();
		}
		InputStream in = null;
		OutputStream out = null;
		if (source.exists()) {
			try {
				in = new FileInputStream(source);
				out = new FileOutputStream(dest);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} finally {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			}
		}
	}

	/**
	 * @param resource
	 *            resourse file
	 * @throws IOException .
	 */
	// delete file's in directory
	public static void delete(File resource) throws IOException {
		if (resource.isDirectory()) {
			File[] childFiles = resource.listFiles();
			for (File child : childFiles) {
				delete(child);
				child.delete();
			}
		} else {
			resource.delete();
		}
		// return resource.delete();
	}

	/**
	 * Append zero to String value at front Eg. appendZeroToStringAtFront(123,5)
	 */
	public static String appendZeroToStringAtFront(String s, int digit) {
		StringBuffer buffer = new StringBuffer();
		for (int x = 0; x < digit - s.length(); x++)
			buffer.append("0");
		buffer.append(s);
		return buffer.toString();
	}

	/**
	 * Append zero to String value at front Eg. appendZeroToStringAtFront(123,5)
	 * == 00123
	 * 
	 * @param number
	 *            .
	 * @param width
	 *            .
	 * @return.
	 */
	public static String appendZeroToStringAtFront(final int number,
			final int width) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < width - Integer.toString(number).length(); i++) {
			result.append("0");
		}
		result.append(Integer.toString(number));
		return result.toString();
	}

	/**
	 * @param directoryList
	 *            .
	 */
	public static void createDirecories(final List<String> directoryList) {
		for (int i = 0; i < directoryList.size(); i++) {
			if (!new File(directoryList.get(i).toString()).exists()) {
				new File(directoryList.get(i).toString()).mkdirs();
			}
		}
	}

	/**
	 * @param fileName
	 *            file name.
	 * @throws IOException
	 */
	public final Long getFileSize(final String fileName) {
		long file_size = 0;
		try {
			File f = new File(fileName);
			if (f.exists()) {
				file_size = f.length();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_size;

	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getLastLineFromFile(String file) throws Exception {
		String lastLine = null;
		FileInputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine = null, tmp;

		while ((tmp = br.readLine()) != null) {
			strLine = tmp;
		}
		lastLine = strLine;
		in.close();
		return lastLine;
	}




	/**
	 * Copy the file from source to destination folder.
	 */
	public static void copyFiles(final String src, final String dest,
			final String fileName) throws IOException {
		try {
			FileUtil.copyFile(new File(src.concat(fileName)), new File(dest,
					fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the first line in the file.
	 * 
	 * @param file
	 * @return
	 */
	public static String getFirstLineFromFile(String filename) {
		FileReader reader = null;
		BufferedReader bufReader = null;
		try {
			reader = new FileReader(filename);
			bufReader = new BufferedReader(reader);
			String line = bufReader.readLine();
			return line;
		} catch (Exception exp) {
			return null;
		} finally {
			try {
				reader.close();
				bufReader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void writeFile(String outputLocation, String fileName,
			List<String> content) {
		try {
			boolean addLine = true;
			if (!(new File(outputLocation.concat(fileName)).exists())) {
				addLine=false;
			}
			FileWriter fw = new FileWriter(outputLocation.concat(fileName),
					true);
			BufferedWriter bw = new BufferedWriter(fw);
			int count = 0;
			if (addLine) {
				bw.newLine();
			}
			//Append files at new Line.
			for (String c : content) {
				count++;
				bw.write(c);
				if (content.size() == count) {
				} else {
					bw.newLine();
				}
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeNewFile(String outputLocation, String fileName,
			List<String> content) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		if (!new File(outputLocation).exists()) {
			new File(outputLocation).mkdirs();
		}

		
		try {
			fw = new FileWriter(outputLocation.concat(fileName), false);
			bw = new BufferedWriter(fw);
			int count = 0;
			for (String c : content) {
				count++;
				if (count==1 && fileName.endsWith("sql")) {
					bw.newLine();
					if (MainParser.WO!=null){
						bw.write("/* "+MainParser.WO+". */");
					}
					bw.write("set echo on;");
					bw.newLine();
					bw.newLine();
					bw.newLine();
				}
				bw.write(c);
				if (content.size() == count) {
					if (fileName.endsWith("sql")) {
						bw.newLine();
						bw.newLine();
						bw.newLine();
						bw.write("commit;");
						bw.newLine();
						bw.write("quit;");
						bw.newLine();
					}
				} else {
					bw.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (bw != null && fw != null) {
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String arrayToString(String[] stringArray, String separator) {
		StringBuffer result = new StringBuffer();
		if (stringArray.length > 0) {
			result.append(stringArray[0]);
			for (int i = 1; i < stringArray.length; i++) {
				result.append(separator);
				result.append(stringArray[i]);
			}
		}
		return result.toString();
	}

	@SuppressWarnings("resource")
	public static List<String> readFileData(String directoryLocation,
			String fileName) {
		FileInputStream fstream = null;
		List<String> contents = null;
		try {
			fstream = new FileInputStream(directoryLocation.concat(fileName));

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			contents = new ArrayList<String>();
			try {
				while ((strLine = br.readLine()) != null) {
					contents.add(strLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return contents;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static File[] dirListByAscendingDate(String directoryLocation) {
		File dir = new File(directoryLocation);

		if (!dir.isDirectory()) {
			return null;
		}
		File files[] = dir.listFiles();
		Arrays.sort(files, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return new Long(((File) o1).lastModified()).compareTo(new Long(
						((File) o2).lastModified()));
			}
		});
		return files;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static File[] dirListByDescendingDate(String directoryLocation) {
		File dir = new File(directoryLocation);
		if (!dir.isDirectory()) {
			return null;
		}
		File files[] = dir.listFiles();
		Arrays.sort(files, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return new Long(((File) o2).lastModified()).compareTo(new Long(
						((File) o1).lastModified()));
			}
		});
		return files;
	}


	public static void main(String[] args) {
		List<String> con = new ArrayList<String>();
		con.add("123");
		con.add("456");
		con.add("234");
		con.add("876");
		con.add("0900000");
		writeNewFile("D:\\meter\\live files\\", "test.txt", con);
	}
}
