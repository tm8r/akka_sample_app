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
			System.out.println("message:" + message + ", path:" + self().path() + ", sender:" + sender().path());
		} else {
			// 想定しない型のメッセージはスルーする
			unhandled(message);
		}
	}

	@Override
	public void preStart() {
		System.out.println("[preStart] path:" + self().path());
	}

	@Override
	public void postStop() {
		System.out.println("[postStop] path:" + self().path());
	}

}
