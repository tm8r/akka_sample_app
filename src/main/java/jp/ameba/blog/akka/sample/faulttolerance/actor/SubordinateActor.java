package jp.ameba.blog.akka.sample.faulttolerance.actor;

import akka.actor.UntypedActor;

/**
 * Supervisorテスト用子Actorクラス
 * @author mihara_tomonari
 */
public class SubordinateActor extends UntypedActor {

	private static int staticState;
	private int state;
	
	@Override
	public void onReceive(Object message) throws Exception {
		staticState++;
		state++;
		if (message instanceof String) {
			int num = Integer.parseInt((String)message);
			System.out.println("message=" + num + ", state=" + state + ", staticState=" + staticState + ", path=" + self().path());
		} else if (message instanceof Integer) {
			int num = (Integer) message;
			if (num < 0) {
				throw new ArithmeticException();
			}
			System.out.println("message=" + num + ", state=" + state + ", staticState=" + staticState + ", path=" + self().path());
		} else {
			throw new IllegalArgumentException("");
		}
	}
	
	@Override
	public void preStart() {
		System.out.println("preStart. state=" + state + ", staticState=" + staticState + ", path=" + self().path());
	}

	@Override
	public void postStop() {
		System.out.println("postStop. state=" + state + ", staticState=" + staticState + ", path=" + self().path());
	}

}
