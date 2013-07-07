package jp.ameba.blog.akka.sample.remote.actor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jp.ameba.blog.akka.sample.remote.message.MessageBean;
import akka.actor.UntypedActor;

/**
 * Remoteテスト用Actor
 * @author mihara_tomonari
 */
public class RemoteActor extends UntypedActor {

	private static String hostName;
	private int state;
	
	static {
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("static initialization completed. hostName=" + hostName);
	}
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			state++;
			System.out.println("message=" + message + ", state=" + state + ", hostName=" + hostName);
		} else if (message instanceof MessageBean) {
			System.out.println("message=MessageBean, hostName=" + hostName);
		} else {
			unhandled(message);
		}
	}
}
