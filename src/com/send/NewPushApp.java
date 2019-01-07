package com.send;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.gexin.rp.sdk.base.impl.AppMessage;
 
 
public class NewPushApp {
 
 
		public static String appId = "jOCZz61IyO97DJgRMIGHc9";
	    public static String appKey = "hEmPtv18i96R7tcV3kCyi";
	    public static String masterSecret = "QW0XFYEEzc7DTbfEFymEC2";
 
	// 定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
	private static String url = "http://sdk.open.api.igexin.com/apiex.htm";//"https://api.getui.com/apiex.htm";
 
	public static void main(String[] args) throws IOException {
		test2("be00a815de4a48a7e042c596538941b1","微信到账1900元");
	}
 
	/**
	 * 推送上方通知栏消息 返回桌面应用可以接收
	 */
	public static void test1(String clientId) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
 
		// 新建消息类型 单独推送给用户采用SingleMessage
		SingleMessage singleMessage = new SingleMessage();
 
		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
		LinkTemplate template = new LinkTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTitle("重要通知！！！！");
		template.setText("测试消息");
		template.setUrl("http://getui.com");
 
		// 设置接收信息的cid集合
		List<String> appIds = new ArrayList<String>();
		appIds.add(appId);
 
		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
		AppMessage message = new AppMessage();
		message.setData(template);
		// 设置接收信息的cid
		message.setAppIdList(appIds);
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(clientId);
		// 推送接口
		// pushMessageToSingle==单个用户/ pushMessageToList==一批用户/
		// pushMessageToApp==应用中的全部用户。
		singleMessage.setData(template);
		IPushResult result = push.pushMessageToSingle(singleMessage, target);
		String response = result.getResponse().toString();
		System.out.println("返回值：" + response);
	}
 
	/**
	 * 用户无法察觉 透传消息
	 */
	public static void test2(String clientId,String msg) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
 
		// 新建消息类型 单独推送给用户采用SingleMessage
		SingleMessage singleMessage = new SingleMessage();
 
		// 新建一个推送模版，已透传模板为例，透传顾名思义到达客户端后不做任何操作，由app选择处理
		// 其他原生模板类型种类很多，支持各种客户端展现效果，包括弹框下载、打开链接等等。模板里也可以设置响铃震动等效果。
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent(msg);
		// 收到消息是否立即启动应用： 1为立即启动，2则广播等待客户端自启动
		template.setTransmissionType(2);
 
		// 模板设置好后塞进message里，同时可以配置这条message是否支持离线，以及过期时间等，单位毫秒
		//若设置离线，个推推送系统会为当前不在线的用户缓存离线消息，个推现在支持为每个用户缓存多条离线消息，离线时间最多三天，三天后会删除该条离线消息
		singleMessage.setData(template);
		singleMessage.setOffline(true);
		singleMessage.setOfflineExpireTime(1800 * 1000);
 
		// 新建一个推送目标，填入appid和clientId
		// 单推情况下只能设置一个推送目标，toList群推时，可以设置多个目标，目前建议一批设置50个左右。
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(clientId);
		// 调用IGtPush实例的toSingle接口，参数就是上面我们配置的message和target
		// 推送接口
		// pushMessageToSingle==单个用户/ pushMessageToList==一批用户/
		// pushMessageToApp==应用中的全部用户。
		IPushResult result = push.pushMessageToSingle(singleMessage, target);
		String response = result.getResponse().toString();
		System.out.println("返回值：" + response);
	}
 
	/**
	 * 透传传参
	 */
	public static void test3(String clientId) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
 
		// 新建消息类型 单独推送给用户采用SingleMessage
		SingleMessage singleMessage = new SingleMessage();
 
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		JSONObject jo = new JSONObject();
		jo.put("AAA", "aaa");
		jo.put("BBB", "bbb");
		jo.put("CCC", "ccc");
		template.setTransmissionContent(jo.toString());
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		// 在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("$由客户端定义");
 
		// 简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
 
		// 字典模式使用APNPayload.DictionaryAlertMsg
		// payload.setAlertMsg(getDictionaryAlertMsg());
 
		// 添加多媒体资源
		payload
				.addMultiMedia(new MultiMedia().setResType(
						MultiMedia.MediaType.video).setResUrl(
						"--")
						.setOnlyWifi(true));
 
		// 需要使用IOS语音推送，请使用VoIPPayload代替APNPayload
		// VoIPPayload payload = new VoIPPayload();
		// JSONObject jo = new JSONObject();
		// jo.put("key1","value1");
		// payload.setVoIPPayload(jo.toString());
		//
		template.setAPNInfo(payload);
		singleMessage.setData(template);
		// 新建一个推送目标，填入appid和clientId
		// 单推情况下只能设置一个推送目标，toList群推时，可以设置多个目标，目前建议一批设置50个左右。
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(clientId);
 
		IPushResult result = push.pushMessageToSingle(singleMessage, target);
		String response = result.getResponse().toString();
		System.out.println("返回值：" + response);
	}
 
	/**
	 * 有透传内容 有标题 点击打开应用
	 * 
	 * @return
	 */
	public static void test4(String clientId) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		// 新建消息类型 单独推送给用户采用SingleMessage
		SingleMessage singleMessage = new SingleMessage();
 
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		
		JSONObject jo = new JSONObject();
		jo.put("AAA", "aba");
		jo.put("BBB", "bab");
		jo.put("CCC", "ccc");
		//template.setTransmissionContent(jo.toJSONString());
		template.setTransmissionContent(jo.toString());
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
 
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("展示标题");
		style.setText("展示内容");
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		singleMessage.setData(template);
 
		// 新建一个推送目标，填入appid和clientId
		// 单推情况下只能设置一个推送目标，toList群推时，可以设置多个目标，目前建议一批设置50个左右。
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(clientId);
		IPushResult result = push.pushMessageToSingle(singleMessage, target);
		String response = result.getResponse().toString();
		System.out.println("返回值：" + response);
	}
 
}
