import java.io.File;
import java.io.FileWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class test {

	private static FileWriter writer = null;
	private static File dir = null;
	static String fileName = null;
	static String filePath = null;
	static String path = "/home/ubuntu";
	static JSONObject jo = new JSONObject();
	static int index = 0;

	public static void main(String[] args) {
		String[] actions = new String[] { "눕기", "걷기", "뛰기", "먹기" };
		Random rand = new Random();

		Person[] person = new Person[10];

		person[0] = new Person(rand.nextInt(1000), rand.nextInt(1000), "E047CC5");
		person[1] = new Person(rand.nextInt(1000), rand.nextInt(1000), "0CCBF88");
		person[2] = new Person(rand.nextInt(1000), rand.nextInt(1000), "6410B1B");
		person[3] = new Person(rand.nextInt(1000), rand.nextInt(1000), "06D35FA");
		person[4] = new Person(rand.nextInt(1000), rand.nextInt(1000), "0D1D060");
		person[5] = new Person(rand.nextInt(1000), rand.nextInt(1000), "6C3F6F5");
		person[6] = new Person(rand.nextInt(1000), rand.nextInt(1000), "D29B9AD");
		person[7] = new Person(rand.nextInt(1000), rand.nextInt(1000), "3725551");
		person[8] = new Person(rand.nextInt(1000), rand.nextInt(1000), "338B653");
		person[9] = new Person(rand.nextInt(1000), rand.nextInt(1000), "7CBBEBE");

		dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		filePath = path + File.separator + "person.log";

		try {

			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {

					try {

						for (int j = 0; j < 10; j++) {
							index++;
							writer = new FileWriter(filePath, true);
							JSONObject jo = new JSONObject();
							JSONArray ja = new JSONArray();
							jo.put("frame", index);

							for (int i = 0; i < 10; i++) {

								int num = rand.nextInt(10);
								person[i].move();
								person[i].action(actions);
								ja.add(person[i].getJson());
							}
							jo.put("person", ja);
							writer.write(jo.toJSONString());
							writer.write("\r\n");
							writer.flush();
							writer.close();
							System.out.println(jo.toJSONString());
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
			timer.schedule(task, 0, 500);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileName = null;
				path = null;
				dir = null;
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class Person {
		int x;
		int y;
		String name;
		String action;

		public Person(int x, int y, String name) {
			this.x = x;
			this.y = y;
			this.name = name;
		}

		public void move() {
			Random rand = new Random();
			int movex = rand.nextInt(3) - 1;
			int movey = rand.nextInt(3) - 1;
			if (this.x >= 1000) {
				movex = -1;
			} else if (this.x <= 0) {
				movex = 1;
			}
			if (this.y >= 1000) {
				movey = -1;
			} else if (this.y <= 0) {
				movey = 1;
			}
			this.x += movex;
			this.y += movey;
		}

		public void action(String[] actions) {
			Random rand = new Random();
			this.action = actions[rand.nextInt(4)];
		}

		public JSONObject getJson() {
			JSONObject jo = new JSONObject();

			jo.put("name", this.name);
			jo.put("action", this.action);
			jo.put("x", this.x);
			jo.put("y", this.y);
			return jo;
		}
	}
}
