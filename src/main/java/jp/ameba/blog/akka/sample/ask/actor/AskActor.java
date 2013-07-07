package jp.ameba.blog.akka.sample.ask.actor;

import akka.actor.UntypedActor;

/**
 * senderにメッセージを送り返すActorクラス
 * @author mihara_tomonari
 */
public class AskActor extends UntypedActor {

	int state = 0;

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			System.out.println("message:" + message);
			sender().tell(++state, self());
		} else {
			System.out.println("unhandled message.");
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
