package jp.ameba.blog.akka.sample.simple.actor;

import akka.actor.UntypedActor;

/**
 * Actorクラス
 * @author mihara_tomonari
 */
public class SimpleActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			System.out.println("message:" + message);
		} else {
			System.out.println("unhandled message.");

			// 想定しない型のメッセージはスルーする
			unhandled(message);
		}
	}

	@Override
	public void preStart() {
		System.out.println("preStart");
	}

	@Override
	public void postStop() {
		System.out.println("postStop");
	}

}
