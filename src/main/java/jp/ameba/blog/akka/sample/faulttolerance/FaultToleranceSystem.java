package jp.ameba.blog.akka.sample.faulttolerance;

import java.util.ArrayList;

import jp.ameba.blog.akka.sample.faulttolerance.actor.MasterSupervisor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * SupervisorとFaultToleranceの実行サンプル
 * @author mihara_tomonari
 */
public class FaultToleranceSystem {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		ActorRef actor = system.actorOf(new Props(MasterSupervisor.class), "simpleActor");

		// send valid string
		System.out.println("send valid string.");
		actor.tell("10", null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}

		System.out.println("-----");

		// send valid string
		System.out.println("send valid string.");
		actor.tell("10", null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// send invalid string
		System.out.println("send invalid string.");
		actor.tell("a", null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// send valid number
		System.out.println("send valid number.");
		actor.tell(1, null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// send invalid number
		System.out.println("send invalid number.");
		actor.tell(-1, null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// send invalid class ArrayList
		System.out.println("send invalid class ArrayList.");
		actor.tell(new ArrayList<>(), null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		// send valid string. not execute because actor is stopped.
		System.out.println("send valid string.");
		actor.tell("10", null);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
		}
		System.out.println("-----");

		system.shutdown();

		System.out.println("end.");
	}
}
