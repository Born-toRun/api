package kr.borntorun.api.adapter.out.thirdparty;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.DeleteObject;
import kr.borntorun.api.adapter.out.thirdparty.model.Remove;
import kr.borntorun.api.adapter.out.thirdparty.model.RemoveAll;
import kr.borntorun.api.adapter.out.thirdparty.model.Upload;
import kr.borntorun.api.support.exception.ClientUnknownException;
import kr.borntorun.api.support.exception.CryptoException;
import kr.borntorun.api.support.exception.InvalidException;
import kr.borntorun.api.support.exception.NetworkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ObjectStorageClient {

  private final MinioClient minioClient;

  public String upload(final Upload resource) {
    try {
      String extension = getFileExtension(resource.file().getOriginalFilename());
      final String uploadedFileName = UUID.randomUUID() + extension;
      log.info(resource.bucket() + "에 " + uploadedFileName + "을 저장합니다.");

      minioClient.putObject(PutObjectArgs.builder()
          .bucket(resource.bucket())
          .object(uploadedFileName)
          .contentType("image/" + extension.substring(1))
          .stream(resource.file().getInputStream(), resource.file().getSize(), -1)
          .build());

      return uploadedFileName;
    } catch (ErrorResponseException | ServerException e) {
      log.error("MinIO 서버 오류: {}", e);
      throw new NetworkException("파일 업로드에 실패하였습니다.");
    } catch (InsufficientDataException e) {
      log.error("파일의 데이터가 전송 도중 끊겼습니다: {}", e);
      throw new NetworkException("파일 업로드에 실패하였습니다.");
    } catch (InternalException e) {
      log.error("MinioClient 오류: {}", e);
      throw new NetworkException("파일 업로드에 실패하였습니다.");
    } catch (InvalidKeyException e) {
      log.error("암호화 키가 잘못되었습니다: {}", e);
      throw new CryptoException("파일 업로드에 실패하였습니다.");
    } catch (InvalidResponseException e) {
      log.error("알 수 없는 오류: {}", e);
      throw new ClientUnknownException("파일 업로드에 실패하였습니다.");
    } catch (IOException e) {
      log.error("파일 입출력 오류: {}", e);
      throw new NetworkException("파일 업로드에 실패하였습니다.");
    } catch (NoSuchAlgorithmException e) {
      log.error("암호화나 해시 등의 알고리즘이 지원되지 않습니다: {}", e);
      throw new CryptoException("파일 업로드에 실패하였습니다.");
    } catch (XmlParserException e) {
      log.error("응답을 파싱할 수 없습니다: {}", e);
      throw new ClientUnknownException("파일 업로드에 실패하였습니다.");
    }
  }

  public void remove(final Remove resource) {
    try {
      minioClient.removeObject(RemoveObjectArgs.builder()
          .bucket(resource.getBucket())
          .object(resource.getObjectName())
          .build());
    } catch (ErrorResponseException | ServerException e) {
      log.error("MinIO 서버 오류: {}", e);
      throw new NetworkException("파일 삭제에 실패하였습니다.");
    } catch (InsufficientDataException e) {
      log.error("파일의 데이터가 전송 도중 끊겼습니다: {}", e);
      throw new NetworkException("파일 삭제에 실패하였습니다.");
    } catch (InternalException e) {
      log.error("MinioClient 오류: {}", e);
      throw new NetworkException("파일 삭제에 실패하였습니다.");
    } catch (InvalidKeyException e) {
      log.error("암호화 키가 잘못되었습니다: {}", e);
      throw new CryptoException("파일 삭제에 실패하였습니다.");
    } catch (InvalidResponseException e) {
      log.error("알 수 없는 오류: {}", e);
      throw new ClientUnknownException("파일 삭제에 실패하였습니다.");
    } catch (IOException e) {
      log.error("파일 입출력 오류: {}", e);
      throw new NetworkException("파일 삭제에 실패하였습니다.");
    } catch (NoSuchAlgorithmException e) {
      log.error("암호화나 해시 등의 알고리즘이 지원되지 않습니다: {}", e);
      throw new CryptoException("파일 삭제에 실패하였습니다.");
    } catch (XmlParserException e) {
      log.error("응답을 파싱할 수 없습니다: {}", e);
      throw new ClientUnknownException("파일 삭제에 실패하였습니다.");
    }
  }

  public void removeAll(final RemoveAll resource) {
    minioClient.removeObjects(
        RemoveObjectsArgs.builder()
            .bucket(resource.getBucket().getBucketName())
            .objects(new ArrayList<>(resource.getObjectNames().stream()
                .map(DeleteObject::new)
                .collect(Collectors.toList())))
            .build()
    );
  }

  private static String getFileExtension(final String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex);
    }

    throw new InvalidException("확장자가 없는 파일은 업로드 할 수 없습니다.");
  }
}
