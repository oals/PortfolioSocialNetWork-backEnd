package com.project.SocialNetworkBackEndSpringBoot.service;

import com.project.SocialNetworkBackEndSpringBoot.dto.FeedFormDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileServiceImpl implements FileService{

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    @Value("${uploadPath}")
    private String uploadPath;

    private final CreateDirectoryService createDirectoryService;


    @Override
    public List<String> uploadFeedImage(List<MultipartFile> list,Long articleNo) {



        List<String> imageFileUrlList = new ArrayList<>();


        if(list.size() != 0) {   //해당 글 내용에 이미지가 있으면

            //이미지 저장 폴더 경로
            String path = itemImgLocation + "/Feed/" + articleNo;  //  글 번호 추가

            //게시글 폴더 생성
            createDirectoryService.CreateDebatePostImageFolder(path);    //이 아티클 번호가 실제 아티클 번호여야 함

            //이미지 파일 업로드
            for(int i = 0; i <  list.size(); i++) {

                //이미지 파일 업로드
                if(!list.get(i).getOriginalFilename().isEmpty()){

                    UUID uuid = UUID.randomUUID();

                    String savedFileName = uuid + list.get(i).getOriginalFilename(); // 난수에 확장자 붙이기
                    String fileUploadFullUrl = path + "/"+ savedFileName;
                    // 결과 : 업로드할 경로 + / + uuid값 + .파일확장자
                    try {
                        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
                        fos.write(list.get(i).getBytes());
                        fos.close();
                    }catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                    imageFileUrlList.add(fileUploadFullUrl);

                }else{
                    imageFileUrlList.add(null);
                }
            }
        }





        //업로드 완료
        return imageFileUrlList;


    }

    @Override
    public String uploadMemberProfileImage(String memberId, MultipartFile profileImage) {

        String path = itemImgLocation + "/Member/" + memberId;  //  글 번호 추가

        //게시글 폴더 생성
        createDirectoryService.CreateDebatePostImageFolder(path);    //


        UUID uuid = UUID.randomUUID();

        String mimeType = profileImage.getContentType();
        String extension = mimeType.split("/")[1]; // "png", "jpeg" 등
        String savedFileName = uuid + "." + extension;
        String fileUploadFullUrl = path + "/"+ savedFileName;
        // 결과 : 업로드할 경로 + / + uuid값 + .파일확장자
        try {
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(profileImage.getBytes());
            fos.close();
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }





        return fileUploadFullUrl;
    }

    @Override
    public void deleteFolder(String delPath) {


        File delFolder = new File(delPath);
        String[] filenames = delFolder.list();

            if(filenames != null) {
                if (filenames.length != 0) {
                    for (int i = 0; i < filenames.length; i++) {

                        File deleteFile = new File(delFolder + "/" + filenames[i]);
                        log.info("삭제할 폴더의 하위 파일 : " + deleteFile.toString());
                        deleteFile.delete();
                    }
                }
            }
        delFolder.delete();





    }

    @Override
    public void deleteFile(String delPath) {
        File delFile = new File(delPath);

        log.info("삭제된 파일 : " + delPath);
        delFile.delete();

    }

    public String toUrl(Path path) {


        String uri = path.toUri().toString();
        return uri.substring(8);

    }

    @Override
    public String resizeImage(String originalImagePath, int width, int height) {
        try {
            String baseName = originalImagePath.substring(0, originalImagePath.lastIndexOf("."));
            String extension = originalImagePath.substring(originalImagePath.lastIndexOf("."));
            Path outputImagePath = Paths.get(baseName + "Resized" + extension);
            Thumbnails.of(originalImagePath)
                    .size(width, height)
                    .toFile(outputImagePath.toString());
            return toUrl(outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
