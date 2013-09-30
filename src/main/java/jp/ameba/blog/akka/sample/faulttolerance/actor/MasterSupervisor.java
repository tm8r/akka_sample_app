package jp.ameba.blog.akka.sample.faulttolerance.actor;

import static akka.actor.SupervisorStrategy.stop;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import akka.routing.RoundRobinRouter;

/**
 * 親Supervisorクラス
 * @author mihara_tomonari
 */
public class MasterSupervisor extends UntypedActor {

	private ActorRef childSupervidor = getContext().actorOf(new Props(ChildSupervisor.class).withRouter(new RoundRobinRouter(3)), "childSup");

	@Override
	public void onReceive(Object message) throws Exception {
		childSupervidor.tell(message, self());
	}

	private static SupervisorStrategy strategy =
			new AllForOneStrategy(10, Duration.create("1 minute"),
					new Function<Throwable, Directive>() {
				@Override
				public Directive apply(Throwable t) {
					System.out.println("[MasterSupervisor] stop. cause:" + t.getClass());
					return stop();
				}
			});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
}
