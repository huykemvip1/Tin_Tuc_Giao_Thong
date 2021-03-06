package com.tintucgiaothong.data.domain;


import java.io.InputStream;
import java.sql.Blob;
import java.time.LocalDateTime;


import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.codec.binary.Base64;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "noidung")
@Slf4j
public class NoiDung {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String content;
	@Convert(converter = String_to_Byte.class)
	private String image;
	private LocalDateTime creation_time;
	@ManyToOne
	@JoinColumn(name = "id_nguoidung",referencedColumnName = "id")
	private NguoiDung nguoiDung;
	@ManyToOne
	@JoinColumn(name="chude_id",referencedColumnName = "id")
	private ChuDe chuDe;
	
	
	
	static class String_to_Byte implements AttributeConverter<String, Blob>{

		@Override
		public Blob convertToDatabaseColumn(String attribute) {
			byte[] bytes=Base64.decodeBase64(attribute);
			try {
				Blob blob=new SerialBlob(bytes);
				return blob;
			}catch(Exception e) {
				log.info(e.getMessage());
			}
			return null;
		}

		@Override
		public String convertToEntityAttribute(Blob dbData) {
			try {
				InputStream inputStream= dbData.getBinaryStream();
				String chuoi=Base64.encodeBase64String(inputStream.readAllBytes());
				return chuoi;
			}catch(Exception e) {
				 log.info(e.getMessage());
			}
			return null;
		}
		
	}
}
