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

@Component
public class FileManager {

	@Autowired
	private ServletContext context;

	public void createFolder(String folderPath, int idx) {
		String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx);

		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public boolean uploadIntoImages(String folderPath, int idx, MultipartFile[] upload) {
		try {
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

				// 1. 원본 가져오기
				byte bytes[] = file.getBytes();

				// 2. 복사 용지 준비
				File outFile = new File(dir, file.getOriginalFilename());

				// 3. 복사기
				FileOutputStream fos = new FileOutputStream(outFile);
				fos.write(bytes); // 복사

				fos.close();
			}

			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean uploadIntoFiles(String folderPath, int idx, MultipartFile[] upload) {
		try {
			for (MultipartFile file : upload) {
				String realPath = context.getRealPath("/resources/" + folderPath + "/" + idx + "/files");

				File dir = new File(realPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// 1. 원본 가져오기
				byte bytes[] = file.getBytes();

				// 2. 복사 용지 준비
				File outFile = new File(dir, file.getOriginalFilename());

				// 3. 복사기
				FileOutputStream fos = new FileOutputStream(outFile);
				fos.write(bytes); // 복사

				fos.close();
			}

			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

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
}
