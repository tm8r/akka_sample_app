package jp.ameba.blog.akka.sample.ask.actor;

import akka.actor.UntypedActor;

/**
 * senderにメッセージを送り返すActorクラス
 * @author mihara_tomonari
 */
public class AskActor extends UntypedActor {

	private int state = 0;

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			System.out.println("message:" + message + ", path:" + self().path() + ", sender:" + sender().path());
			sender().tell(++state, self());
		} else {
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
