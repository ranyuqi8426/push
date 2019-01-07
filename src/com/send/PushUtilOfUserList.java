package com.send;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.base.PushBase;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

// 可接收多个用户，最多为50个
public class PushUtilOfUserList extends PushBase {
	public static Map<String, Object> pushUtil(Msg msg, String[] user) {
		if (user.length > 50) {
			return null;
		}
		// 显示每个用户的用户状态，false:不显示，true：显示
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		// 推送主类
		IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
		// 消息队列
		ListMessage message = new ListMessage();
		// 设置内容
		NotificationTemplate template = notificationTemplate(msg.getTitle(), msg.getHtcontext(), msg.getContext(), msg.getImgName(), msg.getImgNameUrl());
		message.setData(template); // 推送消息消息内容
		// setPriority int 否 消息体的优先级
		// message.setOffline(true); //用户当前不在线时，是否离线存储，可选，默认TRUE
		// message.setOfflineExpireTime(72 * 3600 * 1000); //long 否
		// 默认1h，3600*1000 消息离线存储多久，单位为毫秒，

		// 设置目标
		List<Target> targets = setTargets(user);

		// 推送前通过该接口申请“ContentID”
		String contentId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(contentId, targets);

		System.out.println(ret.getResponse().toString());
		return ret.getResponse();
	}

	/**
	 * 推送内容
	 * 通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以NotificationTemplate
	 * 点击通知打开应用模板
	 * 
	 * @param title 通知标题
	 * @param Htcontext 透传的内容
	 * @param context 通知内容
	 * @param imgName 通知图标，需要客户端开发时嵌入
	 * @param imgUrl 通知图标URL地址
	 * @return
	 */
	private static NotificationTemplate notificationTemplate(String title, String Htcontext, String context, String imgName, String imgUrl) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(APPID); // 应用APPID 设定接收的应用
		template.setAppkey(APPKEY); // 应用APPKEY 用于鉴定身份是否合法
		// setDuration String 否 收到消息的展示时间("2015-01-16 11:40:00", "2015-01-16
		// 12:24:00")
		template.setTransmissionContent(Htcontext);// 透传内容，不支持转义字符
		template.setTransmissionType(1); // 收到消息是否立即启动应用： 1为立即启动，2则广播等待客户端自启动
		// 通知消息内容样式设置 Style0系统样式和Style1个推样式
		Style0 style = new Style0();
		style.setTitle(title); // 通知标题
		style.setText(context); // 通知内容
		style.setLogo(imgName); // 通知图标，需要客户端开发时嵌入
		// style.setLogoUrl(imgUrl); //通知图标URL地址
		// style.setIsRing(false); // 收到通知是否响铃，可选，默认true响铃
		// style.setIsVibrate(false); // 收到通知是否震动，可选，默认true振动
		// style.setIsClearable(true); // 通知是否可清除，可选，默认true可清除
		template.setStyle(style); // 通知栏消息布局样式(Style0 系统样式 Style1 个推样式 Style4
									// 背景图样式 Style6
									// 展开式通知样式)，setStyle是新方法，使用了该方法后原来的设置标题、文本等方法就不起效
		return template;
	}

	/**
	 * 推送目标
	 * 
	 * @param clientid客户端身份ID
	 * @return
	 */
	private static List<Target> setTargets(String[] clientid) {
		// 接收者
		List<Target> targets = new ArrayList<Target>();
		for (int i = 0; i < clientid.length; i++) {
			Target target = new Target();
			target.setAppId(APPID); // 设置客户端所属应用唯一ID APPID
			target.setClientId(clientid[i]); // 设置客户端身份ID ClientID
			targets.add(target);
		}
		return targets;
	}

	public static void main(String[] args) {
		Msg msg = new Msg();
		msg.setTitle("标一");
		msg.setHtcontext("透明一");
		msg.setContext("内一");
		msg.setImgName("push.png");
		String[] users = { "d0ded4a5b160712cffc3075d537ffeb6" };
		pushUtil(msg, users);
	}
}