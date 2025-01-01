package com.chestnut.se_project;


import com.SE_Project.pojo.Activity;
import com.SE_Project.pojo.Group;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;
import com.SE_Project.service.ActivityService;
import com.SE_Project.service.GroupService;

@SpringBootTest(classes = com.SE_Project.SE_ProjectApplication.class)
class SeProjectApplicationTests {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private GroupService groupService;
    MockMvc mockMvc; // 模拟http请求
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // 获取全部活动信息测试
    @Test
    void testGetAllActivity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/get/activity"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // 通过actid获取单一活动信息测试
    @Test
    void testGetActivityDetail() throws Exception {
        // 活动存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/get/detail")
                        .param("actid", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 活动不存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/get/detail")
                        .param("actid", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(0));

    }

    // 通过活动id获取小组信息测试
    @Test
    void testGetTeaminfoByActid() throws Exception {
        // 小组存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/get/teaminfobyactid")
                        .param("actid", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 小组不存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/get/teaminfobyactid")
                        .param("actid", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // 通过openid获取该id创建的所有小组测试
    @Test
    void testGetTeaminfoByOpenid() throws Exception {
        // 小组存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/get/teaminfobyopenid")
                        .param("openid", "o3KOh60182PTOaW6dlN2KN8RWOeo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").isArray());

        // 小组不存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/get/teaminfobyopenid")
                        .param("openid", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").isArray());
    }

    //更新专业测试
    @Test
    void testUpdateMajor() throws Exception {
        String openid = "1";
        String major = "Computer Science";

        mockMvc.perform(MockMvcRequestBuilders.post("/update/myMajor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"openid\": \"" + openid + "\", \"major\": \"" + major + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("User updated successfully"));
    }

    //更新微信号测试
    @Test
    void testUpdateWeixinID() throws Exception {
        String openid = "1";
        String weixinID = "wx12345";

        mockMvc.perform(MockMvcRequestBuilders.post("/update/weixinID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"openid\": \"" + openid + "\", \"weixinID\": \"" + weixinID + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("User updated successfully"));
    }

    //更新电话测试
    @Test
    void testUpdateTel() throws Exception {
        String openid = "1";
        String tel = "1234567890";

        mockMvc.perform(MockMvcRequestBuilders.post("/update/tel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"openid\": \"" + openid + "\", \"tel\": \"" + tel + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("User updated successfully"));
    }

    //更新个人简介测试
    @Test
    void testUpdateIntro() throws Exception {
        String openid = "1";
        String intro = "I am a software engineer.";

        mockMvc.perform(MockMvcRequestBuilders.post("/update/intro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"openid\": \"" + openid + "\", \"intro\": \"" + intro + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("User updated successfully"));
    }

    //更新名字测试
    @Test
    void testUpdateName() throws Exception {
        String openid = "1";
        String name = "testname";

        mockMvc.perform(MockMvcRequestBuilders.post("/update/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"openid\": \"" + openid + "\", \"name\": \"" + name + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("User updated successfully"));
    }

    //更新性别测试
    @Test
    void testUpdateGender() throws Exception {
        String openid = "1";
        String gender = "male";

        mockMvc.perform(MockMvcRequestBuilders.post("/update/gender")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"openid\": \"" + openid + "\", \"gender\": \"" + gender + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("User updated successfully"));
    }

    // 更新头像测试
    @Test
    void testUpdateAvatar() throws Exception {
        // 设置用户的openid和头像文件路径
        String openid = "1";
        String uploadDir = "../images/avatar/1.png";
        Path relativePath = Paths.get(uploadDir);
        byte[] imageBytes = null;

        // 读取文件内容并转换为 Base64 编码
        imageBytes = Files.readAllBytes(relativePath);

        // 创建 MockMultipartFile 对象
        MockMultipartFile file = new MockMultipartFile("file", "avatar.png", MediaType.MULTIPART_FORM_DATA_VALUE, imageBytes);

        // 发送更新头像的请求
        mockMvc.perform(MockMvcRequestBuilders.multipart("/update/avatar")
                        .file(file)
                        .param("openid", openid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("File uploaded successfully."));
    }

    //得到用户信息测试
    @Test
    void testGetUserInfo() throws Exception {
        String openid = "1";

        mockMvc.perform(MockMvcRequestBuilders.get("/get/userinfo")
                        .param("openid", openid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results.openid").value(openid));
    }

    // 创建活动和修改活动信息和删除活动接口测试
    @Test
    void testCreateAct() throws Exception {
        String uploadDir = "test.jpg";
        Path relativePath = Paths.get(uploadDir);
        byte[] imageBytes = null;

        // 判断文件是否存在
        if (Files.exists(relativePath)) {
            // 读取文件内容并转换为 Base64 编码
            imageBytes = Files.readAllBytes(relativePath);
        }

        // 创建 MockMultipartFile 对象
        MockMultipartFile file = new MockMultipartFile("file", "img.png", MediaType.MULTIPART_FORM_DATA_VALUE, imageBytes);

        // 设置活动信息
        String actname = "测试活动";
        String date = "2023-10-1";
        String location = "1";
        String type = "1";
        String organizer = "1";
        String reward = "1";
        String status = "1";
        String permission = "1";
        String introduction = "1";

        // 活动信息有缺
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/post/create_act")
                .file(file)
                .param("actname", actname)
                .param("date", date)
                .param("location", location)
                .param("type", type)
                .param("organizer", organizer)
                .param("reward", reward)
                .param("status", status)
                .param("permission", permission)
                .param("introduction", introduction)
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // 执行请求并断言响应
        // 可能存在主键冲突
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(Matchers.anyOf(Matchers.is(200), Matchers.is(0))));

        // lastActivity变量中存储的就是最后一个遍历到的Activity对象了
        Activity lastActivity = null;
        List<Activity> activityList = activityService.activityList();
        for (Activity act : activityList) {
            lastActivity = act;
        }
        String actid = lastActivity.getActid();
        actname = "Test";

        // 修改活动信息
        MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.multipart("/update/act")
                .file(file)
                .param("actname", actname)
                .param("actid", actid)
                .param("date", date)
                .param("location", location)
                .param("type", type)
                .param("organizer", organizer)
                .param("reward", reward)
                .param("status", status)
                .param("permission", permission)
                .param("introduction", introduction)
                .contentType(MediaType.MULTIPART_FORM_DATA);
        mockMvc.perform(requestBuilder1)
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 删除刚才创建的活动
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/activity")
                        .param("actid", actid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("Record deleted successfully"));
    }

    // 删除活动接口测试（活动不存在时）
    @Test
    void testDeleteAct() throws Exception {
        // 活动不存在的情况
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/activity")
                        .param("actid", "10000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }


    // 创建小组和修改小组和删除小组接口测试
    @Test
    void testChangeGroup() throws Exception {
        // 准备测试数据
        Group group = new Group();
        group.setActid("1");
        group.setDate("2023-10-22");
        group.setGroupname("testgrouptestgrouptestgroup");
        group.setOpenid("123");
        group.setTel("123");
        group.setChargename("test123");
        group.setIntroduction("test123");
        group.setRequired(12);
        group.setCurrent(10);
        group.setRegistered("register 1");
        group.setActname("testtesttest");

        // 先创建小组
        mockMvc.perform(MockMvcRequestBuilders.post("/post/create_group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(group)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(Matchers.anyOf(Matchers.is(200), Matchers.is(0))))
                .andExpect(jsonPath("$.results").value(Matchers.anyOf(Matchers.is("Group inserted successfully"), Matchers.is("Failed to insert group: ..."))));

        // 获取最后创建的小组
        Group lastGroup = null;
        List<Group> GroupList = groupService.groupList();
        for (Group group1 : GroupList) {
            lastGroup = group1;
        }
        String groupid = lastGroup.getGroupid();
        group.setGroupname("testgroup");

        // 修改小组信息
        mockMvc.perform(MockMvcRequestBuilders.put("/put/update_group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(group)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("Group updated successfully"));

        // 删除刚才创建的小组
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/teaminfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"groupid\": \"" + groupid + "\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("Record deleted successfully"));
    }

    // 通过groupid删除小组测试
    @Test
    void testDeleteGroup() throws Exception {
        // 小组不存在的情况
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/teaminfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"groupid\": \"10000\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("Record deleted successfully"));
    }

    // 搜索测试
    @Test
    void searchTest() throws Exception {
        // 查找相关奖励
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage/search")
                        .param("keyward", "荣誉证书"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results[*].reward", everyItem(containsString("荣誉证书"))));

        // 查找相关地点
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage/search")
                        .param("keyward", "致理楼"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results[*].location", everyItem(containsString("致理楼"))));

        // 查找相关类型
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage/search")
                        .param("keyward", "义工活动"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results[*].type", everyItem(containsString("义工活动"))));

        // 没有输入，返回所有
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage/search")
                        .param("keyward", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 查询不存在的活动
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage/search")
                        .param("keyward", "不存在的活动"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results", empty()));
    }

    // 筛选测试
    @Test
    void selectTest() throws Exception{
        // 构建请求体 JSON 字符串
        String jsonRequestBody = "{ \"selectedItems\": [\"不限\"] }";
        // 类型不限
        mockMvc.perform(MockMvcRequestBuilders.post("/homepage/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 单一活动类型
        jsonRequestBody = "{ \"selectedItems\": [\"义工活动\"] }";
        mockMvc.perform(MockMvcRequestBuilders.post("/homepage/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results[*].type", everyItem(containsString("义工活动"))));

        // 多个活动类型
        jsonRequestBody = "{ \"selectedItems\": [\"义工活动\",\"实习\"] }";
        mockMvc.perform(MockMvcRequestBuilders.post("/homepage/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results[*].type", everyItem(anyOf(containsString("义工活动"), containsString("实习")))));
    }

    // 通过openid获取已创建的所有未满小组信息测试
    @Test
    void testGetResGroupByOpenid() throws Exception {
        // 小组存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/getResGroup")
                        .param("openid", "o3KOh60182PTOaW6dlN2KN8RWOeo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").isArray());

        // 小组不存在的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/getResGroup")
                        .param("openid", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").isArray());
    }

    // 轮播图更新接口测试
    @Test
    void testUpdateCarousel() throws Exception {
        //活动不存在时
        mockMvc.perform(MockMvcRequestBuilders.post("/update/carousel")
                        .param("actid", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("Carousel updated successfully"));

        //活动存在时
        mockMvc.perform(MockMvcRequestBuilders.post("/update/carousel")
                        .param("actid", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.results").value("Carousel updated successfully"));
    }

    // 更新搜索历史接口测试
    @Test
    void testUpdateHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/update/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"openid\": \"10000\",\"history\": \"\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("History uploaded successfully."));
    }

    // 队伍报名接口和用户删除小组/小组里删除用户测试
    @Test
    void testSignupAndCancelsign() throws Exception {
        // 测试队伍报名接口
        String openid = "1";
        String groupid = "72";

        JSONObject requestBody = new JSONObject();

        requestBody.put("openid", openid);
        requestBody.put("groupid", groupid);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("Sign up successfully!"));

        requestBody.put("openid", "nothing");
        requestBody.put("groupid", groupid);
        // 用户不存在
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(404))   // 断言测试
                .andExpect(jsonPath("$.results").value("Failed to sign up: " +
                        "Team/User not found."));

        requestBody.put("openid", "1");
        requestBody.put("groupid", "nothing");
        // 队伍不存在
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(404))   // 断言测试
                .andExpect(jsonPath("$.results").value("Failed to sign up: " +
                        "Team/User not found."));

        // 测试删除队伍里的用户接口
        mockMvc.perform(MockMvcRequestBuilders.post("/cancelsign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").value("Successfully deleted"));

        // 队伍里已存在该用户的情况
        openid = "o3KOh62IIJyAhOK9YuVIIOpJDlic";
        groupid = "72";
        requestBody.put("openid", openid);
        requestBody.put("groupid", groupid);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(100))
                .andExpect(jsonPath("$.results").value("The team already exists in the list " +
                        "of participating teams for that member!"));
    }

    // 获取已参加队伍名单（用户）接口测试
    @Test
    void testGetRegGroup() throws Exception {
        // 已参加队伍的用户
        String openid = "1";
        mockMvc.perform(MockMvcRequestBuilders.get("/getRegGroup")
                        .param("openid", openid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 未参加任何队伍的用户
        openid = "10000";
        mockMvc.perform(MockMvcRequestBuilders.get("/getRegGroup")
                        .param("openid", openid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // 获取头像接口测试
    @Test
    void testGetAvatar() throws Exception {
        // 用户已上传过头像
        String openid = "1";
        mockMvc.perform(MockMvcRequestBuilders.get("/get_avatar")
                        .param("openid", openid))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // 通过groupid查询小组下的所有成员接口测试
    @Test
    void testGetUserByGroupid() throws Exception {
        // 存在的小组ID
        String groupid = "1";
        mockMvc.perform(MockMvcRequestBuilders.get("/getUserByGroupid")
                        .param("groupid", groupid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 不存在的小组ID
        groupid = "10000";
        mockMvc.perform(MockMvcRequestBuilders.get("/getUserByGroupid")
                        .param("groupid", groupid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    //得到用户额外信息接口测试
    @Test
    void testGetExtraUserInfo() throws Exception {
        String openid = "1";
        mockMvc.perform(MockMvcRequestBuilders.get("/get/extra_user_Info")
                        .param("openid", openid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.results").isNotEmpty());
    }
}

