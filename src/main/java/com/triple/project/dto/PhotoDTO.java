package com.triple.project.dto;

import com.triple.project.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
@Getter
public class PhotoDTO {
	private String photoId;

	public static List<Photo> toPhotos(List<String> photoIds) {
		List<Photo> photos = new ArrayList<>();
		photoIds.forEach(photoId -> {
			photos.add(new Photo(photoId));
		});
		return photos;
	}

	public static boolean isEqualPhotos(List<String> photos1, List<Photo> photos2) {
		List<String> photos2Ids = new ArrayList<>();
		photos2.forEach(photo2 -> {
			photos2Ids.add(photo2.getId());
		});
		return Arrays.equals(new List[]{photos1}, new List[]{photos2Ids});
	}
}
