package com.cc.common.Utils;

import com.ancun.netsign.client.NetSignClient;
import com.ancun.netsign.model.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AncunUtil {
    private static final String API_KEY = "ee7fd1aac95dacb9c5d8b1928717d010";
    private static final String API_SECRET = "dfed0ce85b7da92ac6fc3aec12a3acb4";

    private static NetSignClient netSignClient ;

    static {
        netSignClient = new NetSignClient("http://pre-openapi.acsign.cn/openApi/",
                API_KEY, API_SECRET);
    }

    /**
     * 同步用户接口
     */

    public static NetSignResponse addUser(String realName,String identity,String mobile,String email) {

        Integer type = 2;
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setName(realName);
        addUserRequest.setIdNo(identity);
        addUserRequest.setMobile(mobile);
        addUserRequest.setEmail(email);
        addUserRequest.setType(type);
        addUserRequest.setIdentifyType(1);
        addUserRequest.setIdentifyMobile(mobile);

        NetSignResponse netSignResponse = netSignClient.addUser(addUserRequest);

        return netSignResponse;
    }

    /**
     * 同步用户并制定签名文件
     * @throws FileNotFoundException
     */
    public void addUserAndSealTest() throws FileNotFoundException {
        String filePath = "filePath";

        String name = "name";
        String idNo = "idNo";
        String mobile = "mobile";
        String email = "email";
        Integer type = 2;

        FileDto sealImage = new FileDto();
        sealImage.setFileName("fileName.png");
        sealImage.setFilePath(filePath);

        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setName(name);
        addUserRequest.setIdNo(idNo);
        addUserRequest.setMobile(mobile);
        addUserRequest.setEmail(email);
        addUserRequest.setType(type);
        addUserRequest.setIdentifyType(1);
        addUserRequest.setIdentifyMobile(mobile);

        System.out.println(netSignClient.addUser(addUserRequest, sealImage));
    }

    /**
     * 查询用户信息接口
     */

    public static void getUserTest() {
        String name = "陈成坤";
        String idNo = "411403198710065779";
        System.out.println(netSignClient.getUser(name, idNo));
    }

    /**
     * 上传模板文件
     * @throws FileNotFoundException
     */

    public static void uploadTemplateTest() {
        String templateIdent = "t00010";
        String templateName = "t00010";
        String templateFilePath = "D:\\成绩.pdf";
        FileDto template = new FileDto();
        template.setFileName("成绩.pdf");
        template.setFilePath(templateFilePath);
        UploadTemplateRequest uploadTemplateRequest = new UploadTemplateRequest();
        uploadTemplateRequest.setTemplateIdent(templateIdent);
        uploadTemplateRequest.setTemplateName(templateName);
        System.out.println(netSignClient.uploadTemplate(uploadTemplateRequest, template));
    }

    /**
     * 下载模板文件
     * @throws IOException
     */

   /* public static void downloadTemplateTest() throws IOException {
        String templateIdent = "t001";
        String templateFilePath = "D:\\";

        NetSignDownloadResponse downloadResult = netSignClient.downloadTemplate(templateIdent);
        System.out.println(JSON.toJSONString(downloadResult));
        if (downloadResult.getCode() == 100000) {
            InputStream is = downloadResult.getData().getObjectContent();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }
            FileOutputStream fos = new FileOutputStream(templateFilePath + downloadResult.getData().getFileName());
            output.writeTo(fos);
            System.out.println(downloadResult.getData().getFileName());
        }
    }*/

    /**
     * 使用模板发起合同接口
     */

    public void initiatingContractTest() {
        String contractFilePath = "D:\\爱签接口文档.pdf";
        String contractName = "ContractNo1000";
        String contractNo = "Contract1000";
        Integer validityTime = 365;
        List<ContractUser> contractUserList = new ArrayList();
        ContractUser contractUser = new ContractUser();
        contractUser.setIdNo("411403198710065779");
        contractUser.setName("陈成坤");
        contractUser.setSignOrder(0);
        contractUser.setSignMode(2);
        contractUser.setLocationMode(2);
        contractUser.setSignType(3);


        List<SignStrategy> signStrategyList = new ArrayList();
        signStrategyList.add(new SignStrategy(contractName, ""));
        contractUser.setSignStrategyList(signStrategyList);

        contractUserList.add(contractUser);

        List<ContractAttach> contractAttachList = new ArrayList();
        ContractAttach contractAttach = new ContractAttach();
        contractAttach.setTemplateIdent("t00010");
        contractAttach.setAttachName(contractFilePath);
        contractAttach.setSequence(1);
        contractAttachList.add(contractAttach);

        InitiatingContractRequest initiatingContractRequest = new InitiatingContractRequest();
        initiatingContractRequest.setContractNo(contractNo);
        initiatingContractRequest.setContractName(contractName);
        initiatingContractRequest.setValidityTime(validityTime);
        initiatingContractRequest.setContractUserList(contractUserList);
        initiatingContractRequest.setContractAttachList(contractAttachList);
        initiatingContractRequest.setSignOrder(0);

        System.out.println(netSignClient.initiatingContract(initiatingContractRequest));
    }

    /**
     * 使用自定义合同文件发起合同
     * @param contractId 合同ID
     * @param contractName 合同名
     * @param fileInputStream  文件流
     * @param realName 真实姓名
     * @param identity 用户唯一ID （身份证） 必须是实名认证过的
     */
    public static NetSignResponse initiatingContractCustomFile(String contractId,String contractName,InputStream fileInputStream,String realName,String identity) {

        Integer validityTime = 365;
        List<ContractUser> contractUserList = new ArrayList();
        ContractUser contractUser = new ContractUser();
        contractUser.setIdNo(identity);
        contractUser.setName(realName);
        contractUser.setSignMode(2);
        contractUser.setLocationMode(2);
        contractUser.setSignType(3);


        List<SignStrategy> signStrategyList = new ArrayList();
        SignStrategy strategy = new SignStrategy(contractName, "");
        strategy.setSignX(400);
        signStrategyList.add(strategy);
        contractUser.setSignStrategyList(signStrategyList);
        contractUserList.add(contractUser);

        List<ContractAttach> contractAttachList = new ArrayList();
        ContractAttach contractAttach = new ContractAttach();

        contractAttach.setAttachName(contractName);
        contractAttach.setSequence(1);

        contractAttachList.add(contractAttach);

        FileDto contractFile = new FileDto();
        contractFile.setFileInputStream(fileInputStream);
        contractFile.setFileName(contractName);


        InitiatingContractRequest request = new InitiatingContractRequest();

        request.setContractNo(contractId);
        request.setContractName(contractName);
        request.setValidityTime(validityTime);
        request.setContractUserList(contractUserList);
        request.setContractAttachList(contractAttachList);

        NetSignResponse response = netSignClient.initiatingContract(request, contractFile);


        System.out.println(response);


        return response;
    }
    /**
     * 查询合同状态接口
     */

    public void getContractStatus() {
        String contractNo = "contractNo";
        System.out.println(netSignClient.getContractStatus(contractNo));
    }

    /**
     * 查询合同信息接口
     */

    public void getContractInfo() {
        String contractNo = "contractNo";
        System.out.println(netSignClient.getContractInfo(contractNo));
    }

    /**
     * 下载合同接口
     * @throws IOException
     */

    public static void downloadContractTest() throws IOException {
        String contractNo = "ContractNo008";
        String contractFilePath = "E:\\";

        NetSignDownloadResponse downloadResult = netSignClient.downloadContract(contractNo);
        if (downloadResult.getCode() == 100000) {
            InputStream is = downloadResult.getData().getObjectContent();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }
            FileOutputStream fos = new FileOutputStream(contractFilePath + downloadResult.getData().getFileName());
            output.writeTo(fos);
            System.out.println(downloadResult.getData().getFileName());
        }
    }


    /**
     * 下载合同接口
     * @throws IOException
     */

    public static String downloadContract(String savePath,String contractNo) throws IOException {
        NetSignDownloadResponse downloadResult = netSignClient.downloadContract(contractNo);
        if (downloadResult.getCode() == 100000) {
            InputStream is = downloadResult.getData().getObjectContent();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }
            //downloadResult.getData().getFileName()
            FileOutputStream fos = new FileOutputStream(savePath + contractNo+".pdf");
            output.writeTo(fos);
            is.close();
            fos.flush();
            fos.close();
            output.flush();
            output.close();
            return savePath +contractNo+ ".pdf"/*downloadResult.getData().getFileName()*/;
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\xx.pdf");

        AncunUtil.initiatingContractCustomFile(UUID.randomUUID().toString(),"test.pdf",new FileInputStream(file),"陈成坤","411403198710065779");


       // AncunUtil.downloadContract("D:\\","b92f48b9-e5c2-41f1-95e1-860ff3d5f1d4");


    }
}
