package com.cc.common.Utils;

import com.alibaba.fastjson.JSON;
import com.ancun.netsign.client.NetSignClient;
import com.ancun.netsign.model.*;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.net.util.Base64;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addUserTest() {
        String name = "name";
        String idNo = "idNo";
        String mobile = "mobile";
        String email = "email";
        Integer type = 2;

        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setName(name);
        addUserRequest.setIdNo(idNo);
        addUserRequest.setMobile(mobile);
        addUserRequest.setEmail(email);
        addUserRequest.setType(type);
        addUserRequest.setIdentifyType(1);
        addUserRequest.setIdentifyMobile(mobile);

        System.out.println(netSignClient.addUser(addUserRequest));
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

    public static void downloadTemplateTest() throws IOException {
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
    }

    /**
     * 使用模板发起合同接口
     */

    public void initiatingContractTest() {
        String contractName = "contractName";
        String contractNo = "contractNo";
        Integer validityTime = 10;

        List<ContractUser> contractUserList = new ArrayList<ContractUser>();

        ContractUser contractUser = new ContractUser();
        contractUser.setIdNo("ContractUserIdNo");
        contractUser.setName("contractUserName");
        contractUser.setSignOrder(0);
        contractUser.setLocationMode(3);

        List<SignStrategy> signStrategyList = new ArrayList<>();
        signStrategyList.add(new SignStrategy("templateIdent", "keyWord1"));
        signStrategyList.add(new SignStrategy("attachName.pdf", "keyWord2"));

        contractUser.setSignStrategyList(signStrategyList);
        contractUserList.add(contractUser);

        ContractUser contractUser1 = new ContractUser();
        contractUser1.setIdNo("contractUser1IdNo");
        contractUser1.setName("contractUser1Name");
        contractUser1.setLocationMode(3);

        List<SignStrategy> signStrategyList1 = new ArrayList<>();
        signStrategyList1.add(new SignStrategy("templateIdent", "page1", 100, 100));
        signStrategyList1.add(new SignStrategy("attachName.pdf", "page2", 100, 100));

        contractUser1.setSignStrategyList(signStrategyList1);
        contractUserList.add(contractUser1);

        List<ContractAttach> contractAttachList = new ArrayList<>();
        ContractAttach contractAttach = new ContractAttach();
        contractAttach.setAttachName("templateIdent");
        contractAttach.setTemplateIdent("templateIdent");
        contractAttach.setSequence(1);
        contractAttachList.add(contractAttach);

        ContractAttach contractAttach2 = new ContractAttach();
        contractAttach2.setAttachName("attachName.pdf");
        contractAttach2.setSequence(2);
        contractAttachList.add(contractAttach2);

        FileDto contractFile = new FileDto();
        contractFile.setFileName("attachName.pdf");
        contractFile.setFilePath("E:\\contract\\attachName.pdf");

        InitiatingContractRequest initiatingContractRequest = new InitiatingContractRequest();
        initiatingContractRequest.setContractNo(contractName + contractNo);
        initiatingContractRequest.setContractName(contractName);
        initiatingContractRequest.setValidityTime(validityTime);initiatingContractRequest.setContractUserList(contractUserList);
        initiatingContractRequest.setContractAttachList(contractAttachList);

        System.out.println(netSignClient.initiatingContract(initiatingContractRequest, contractFile));
    }

    /**
     * 使用自定义合同文件发起合同
     */

    public static void initiatingContractAndFileTest() {

    }


    public static void initiatingContractTest2() {
        String contractFilePath = "D:\\成绩.pdf";
        String contractName = "ContractNo008";
        String contractNo = "ContractNo008";
        Integer validityTime = 10;
        List<ContractUser> contractUserList = new ArrayList();
        ContractUser contractUser = new ContractUser();
        contractUser.setIdNo("411403198710065779");
        contractUser.setName("陈成坤");
        contractUser.setSignOrder(0);
        contractUser.setSignMode(2);
        contractUser.setLocationMode(2);
        contractUser.setSignType(3);


        List<SignStrategy> signStrategyList = new ArrayList();
        signStrategyList.add(new SignStrategy(contractName, "排名"));
        contractUser.setSignStrategyList(signStrategyList);

        contractUserList.add(contractUser);



        List<ContractAttach> contractAttachList = new ArrayList();
        ContractAttach contractAttach = new ContractAttach();
        contractAttach.setTemplateIdent("t00010");
        contractAttach.setAttachName(contractName);
        contractAttach.setSequence(1);
        contractAttachList.add(contractAttach);


        FileDto contractFile = new FileDto();
        contractFile.setFileName("attachName.pdf");
        contractFile.setFilePath(contractFilePath);

        InitiatingContractRequest initiatingContractRequest = new InitiatingContractRequest();
        initiatingContractRequest.setContractNo(contractNo);
        initiatingContractRequest.setContractName(contractName);
        initiatingContractRequest.setValidityTime(validityTime);
        initiatingContractRequest.setContractUserList(contractUserList);
        initiatingContractRequest.setContractAttachList(contractAttachList);
        initiatingContractRequest.setSignOrder(0);

        System.out.println(netSignClient.initiatingContract(initiatingContractRequest, contractFile));
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

    public static String downloadContract(String savePath,String contractNo, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ServletOutputStream out = response.getOutputStream();

        NetSignDownloadResponse downloadResult = netSignClient.downloadContract(contractNo);
        if (downloadResult.getCode() == 100000) {
            InputStream is = downloadResult.getData().getObjectContent();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }
            FileOutputStream fos = new FileOutputStream(savePath + downloadResult.getData().getFileName());
            output.writeTo(fos);
            is.close();
            fos.flush();
            fos.close();
            output.flush();
            output.close();
            return savePath + downloadResult.getData().getFileName();
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        AncunUtil.downloadContractTest();
    }
}
