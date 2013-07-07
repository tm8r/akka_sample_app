package jp.ameba.blog.akka.sample.faulttolerance.actor;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;

/**
 * 子Supervisorクラス
 * @author mihara_tomonari
 */
public class ChildSupervisor extends UntypedActor {

	private ActorRef childActor = getContext().actorOf(new Props(SubordinateActor.class), "childActor");

	@Override
	public void onReceive(Object message) throws Exception {
		childActor.tell(message, null);
	}

	private static SupervisorStrategy strategy =
			new OneForOneStrategy(10, Duration.create("1 minute"),
					new Function<Throwable, Directive>() {
				@Override
				public Directive apply(Throwable t) {
					if (t instanceof ArithmeticException) {
						System.out.println("[ChildSupervisor] resume. cause:" + t.getClass());
						return resume();
					} else if (t instanceof NumberFormatException) {
						System.out.println("[ChildSupervisor] restart. cause:" + t.getClass());
						return restart();
					} else {
						System.out.println("[ChildSupervisor] escalate. cause:" + t.getClass());
						return escalate();
					}
				}
			});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
}
