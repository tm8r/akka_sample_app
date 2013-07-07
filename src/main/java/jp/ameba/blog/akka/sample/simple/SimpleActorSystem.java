package jp.ameba.blog.akka.sample.simple;

import jp.ameba.blog.akka.sample.simple.actor.SimpleActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * シンプルなActor実行サンプル
 * @author mihara_tomonari
 */
public class SimpleActorSystem {

	public static void main(String[] args) {
		// ActorSystemを生成
		ActorSystem system = ActorSystem.create("system");

		// ActorSystemを用いてActorを生成
		ActorRef ref = system.actorOf(new Props(SimpleActor.class), "simpleActor");

		String message = "hello.";

		// Actorに対してメッセージを送信する
		ref.tell(message, null);

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		system.shutdown();

		System.out.println("end.");
	}
}
