package jp.ameba.blog.akka.sample.faulttolerance.actor;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;

/**
 * 親Supervisorクラス
 * @author mihara_tomonari
 */
public class MasterSupervisor extends UntypedActor {

	private ActorRef childSupervidor = getContext().actorOf(new Props(ChildSupervisor.class), "childSup");

	@Override
	public void onReceive(Object message) throws Exception {
		childSupervidor.tell(message, self());
	}

	private static SupervisorStrategy strategy =
			new OneForOneStrategy(10, Duration.create("1 minute"),
					new Function<Throwable, Directive>() {
				@Override
				public Directive apply(Throwable t) {
					if (t instanceof ArithmeticException) {
						System.out.println("[MasterSupervisor] resume. cause:" + t.getClass());
						return resume();
					} else if (t instanceof NumberFormatException) {
						System.out.println("[MasterSupervisor] restart. cause:" + t.getClass());
						return restart();
					} else {
						System.out.println("[MasterSupervisor] stop. cause:" + t.getClass());
						return stop();
					}
				}
			});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
}
