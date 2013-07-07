package jp.ameba.blog.akka.sample.remote;

import java.util.ArrayList;
import java.util.List;

import jp.ameba.blog.akka.sample.remote.actor.RemoteActor;
import jp.ameba.blog.akka.sample.remote.message.MessageBean;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Deploy;
import akka.actor.Props;
import akka.kernel.Bootable;
import akka.remote.RemoteScope;
import akka.routing.BroadcastRouter;
import akka.routing.RoundRobinRouter;

import com.typesafe.config.ConfigFactory;

/**
 * リモートテスト用ローカル実行クラス
 * @author mihara_tomonari
 */
public class LocalSystem implements Bootable {

	final ActorSystem system = ActorSystem.apply("localSys", ConfigFactory
			.load().getConfig("localSetting"));

	private static final String AKKA_PROTOCOL = "akka";
	private static final String REMOTE_SYSTEM_NAME = "remoteSys";
	private static final String REMOTE_HOST_IP = "172.28.139.133";
	private static final int REMOTE_HOST_PORT = 2552;

	@Override
	public void startup() {
		String message = "hello.";

		// local actor test
		System.out.println("local actor test.");
		ActorRef localActor = system.actorOf(new Props(RemoteActor.class),
				"localActor");
		localActor.tell(message, null);

		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// remote actor test
		System.out.println("remote actor test.");
		ActorRef remoteActor = system.actorOf(new Props(RemoteActor.class)
		.withDeploy(new Deploy(new RemoteScope(new Address(
				AKKA_PROTOCOL, REMOTE_SYSTEM_NAME, REMOTE_HOST_IP,
				REMOTE_HOST_PORT)))), "remoteActor");
		remoteActor.tell(message, null);

		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// router test with RoundRobinRouter
		System.out.println("router test with RoundRobinRouter.");
		List<ActorRef> actors = new ArrayList<>();
		actors.add(remoteActor);
		actors.add(localActor);
		ActorRef roundRobinRouter = system.actorOf(new Props(RemoteActor.class)
		.withRouter(RoundRobinRouter.create(actors)));
		for (int i = 0; i < 6; i++) {
			String roundRobinMessage = message + i;
			roundRobinRouter.tell(roundRobinMessage, null);
		}

		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// router test with BroadcastRouter
		System.out.println("router test with BroadcastRouter.");
		ActorRef broadcastRouter = system.actorOf(new Props(RemoteActor.class)
		.withRouter(BroadcastRouter.create(actors)));
		broadcastRouter.tell(message, null);

		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// send MessageBean class
		System.out.println("send MessageBean.");
		for (int i = 0; i < 2; i++) {
			roundRobinRouter.tell(new MessageBean(), null);
		}
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}

		system.shutdown();

		System.out.println("end.");
	}

	@Override
	public void shutdown() {
		if (system.isTerminated() == false) {
			system.shutdown();
		}
	}

}
