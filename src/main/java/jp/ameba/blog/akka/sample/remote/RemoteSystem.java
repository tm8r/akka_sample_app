package jp.ameba.blog.akka.sample.remote;

import akka.actor.ActorSystem;
import akka.kernel.Bootable;

import com.typesafe.config.ConfigFactory;

/**
 * リモートテスト用リモート実行クラス
 * @author mihara_tomonari
 */
public class RemoteSystem implements Bootable {
	final ActorSystem system = ActorSystem.create("remoteSys", ConfigFactory.load().getConfig("remoteSetting"));

	@Override
	public void startup() {
		System.out.println("start");
	}
	
	@Override
	public void shutdown() {
		system.shutdown();
	}

}
