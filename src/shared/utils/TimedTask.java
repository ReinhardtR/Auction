package shared.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

abstract public class TimedTask {
	public static void runTask(Consumer<Timer> task, int intervalTimeout) {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				task.accept(timer);
			}
		}, 0, intervalTimeout);
	}
}
