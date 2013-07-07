package jp.ameba.blog.akka.sample.ask;

import java.util.concurrent.TimeUnit;

import jp.ameba.blog.akka.sample.ask.actor.AskActor;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;

/**
 * Actorから結果を受け取るサンプル
 * @author mihara_tomonari
 */
public class AskActorSystem {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		ActorRef actor = system.actorOf(new Props(AskActor.class), "askActor");

		String message = "hello.";

		Integer result = 0;
		try {
			// メッセージを送信し、結果を受け取る
			result = (Integer) Await.result(Patterns.ask(actor, message, 5000), Duration.create(5000, TimeUnit.MILLISECONDS));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("result=" + result);

		try {
			result = (Integer) Await.result(Patterns.ask(actor, message, 5000), Duration.create(5000, TimeUnit.MILLISECONDS));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("result=" + result);

		try {
			// 想定しない型を送信する。Exceptionが発生する
			result = (Integer) Await.result(Patterns.ask(actor, 1, 5000), Duration.create(5000, TimeUnit.MILLISECONDS));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("result=" + result);

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		system.shutdown();

		System.out.println("end.");
	}
}
