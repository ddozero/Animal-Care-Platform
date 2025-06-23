package com.animal.api.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관련 기능을 수행할 수 있는 유틸 클래스
 * 
 * @author Rege-97
 * @since 2026-06-23
 */
@Component
public class FileManager {

	@Autowired
	private ServletContext context;

	/**
	 * 이미지 업로드 처리 메서드
	 * 
	 * @param folderPath 이미지를 업로드할 폴더 이름(테이블명)
	 * @param idx        해당 테이블 기본키
	 * @param upload     업로드할 파일들
	 * @return 이미지 업로드 성공 여부
	 */
	public boolean uploadImages(String folderPath, int idx, MultipartFile[] upload) {
		try {

			int count = 0;
			for (MultipartFile file : upload) {

				String contentType = file.getContentType();
				if (contentType == null || !contentType.startsWith("image/")) {
					return false; // 이미지 아니면 즉시 실패 반환
				}

				String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx + "/images");

				File dir = new File(realPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				byte bytes[] = file.getBytes();
				String originalFileName = file.getOriginalFilename();
				// 이미지는 0부터 순번으로 파일 이름 변경
				String saveFileName = count + "."
						+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

				File outFile = new File(dir, saveFileName);
				FileOutputStream fos = new FileOutputStream(outFile);
				fos.write(bytes);
				fos.close();

				count++;
			}

			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 첨부파일 업로드 처리 메서드
	 * 
	 * @param folderPath 첨부파일을 업로드할 폴더 이름(테이블명)
	 * @param idx        해당 테이블 기본키
	 * @param upload     업로드할 파일들
	 * @return 첨부파일 업로드 성공 여부
	 */
	public boolean uploadFiles(String folderPath, int idx, MultipartFile[] upload) {
		try {
			for (MultipartFile file : upload) {
				String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx + "/files");

				File dir = new File(realPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				byte bytes[] = file.getBytes();
				File outFile = new File(dir, file.getOriginalFilename());
				FileOutputStream fos = new FileOutputStream(outFile);
				fos.write(bytes);
				fos.close();
			}

			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 첨부파일들의 경로를 반환하는 메서드
	 * 
	 * @param folderPath 반환할 첨부파일의 폴더명(테이블명)
	 * @param idx        해당 테이블 기본키
	 * @return 설정한 폴더 내부의 파일들의 경로
	 */
	public List<String> getFilePath(String folderPath, int idx) {
		String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx + "/files");
		File dir = new File(realPath);
		if (!dir.exists()) {
			return null;
		}

		File[] files = dir.listFiles();
		List<String> filesPath = new ArrayList<String>();
		for (File f : files) {
			filesPath.add("/resources/" + folderPath + "/" + idx + "/files/" + f.getName());
		}

		return filesPath;
	}

	/**
	 * 이미지들의 경로를 반환하는 메서드
	 * 
	 * @param folderPath 반환할 이미지의 폴더명(테이블명)
	 * @param idx        해당 테이블 기본키
	 * @return 설정한 폴더 내부의 이미지들의 경로
	 */
	public List<String> getImagePath(String folderPath, int idx) {
		String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx + "/images");
		File dir = new File(realPath);
		if (!dir.exists()) {
			return null;
		}
		
		File[] files = dir.listFiles();
		List<String> filesPath = new ArrayList<String>();
		for (File f : files) {
			filesPath.add("/resources/" + folderPath + "/" + idx + "/images/" + f.getName());
		}

		return filesPath;
	}


	public void deleteFolder(String folderPath, int idx) {
		String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx);
		File dir = new File(realPath);
		if (dir.exists()) {
			deleteRecursive(dir);
		}
	}

	private void deleteRecursive(File file) {
		if (file.isDirectory()) {
			for (File sub : file.listFiles()) {
				deleteRecursive(sub);
			}
		}
		file.delete();
	}

	public void deleteFile(String filePath) {
		File f = new File(context.getRealPath(filePath));
		if (f.exists()) {
			f.delete();
		}
	}
}
