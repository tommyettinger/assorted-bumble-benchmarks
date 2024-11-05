/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.examples;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedSet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.github.tommyettinger.random.FourWheelRandom;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import squidpony.StringKit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * KryoWriteBench score: 666.036682 (666.0 650.1%)
 *            uncertainty:   0.7%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * KryoWriteBench score: 629.724426 (629.7 644.5%)
 *            uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * KryoWriteBench score: 645.699951 (645.7 647.0%)
 *            uncertainty:   0.7%
 * <br>
 * GraalVM Java 22:
 * <br>
 * KryoWriteBench score: 741.616821 (741.6 660.9%)
 *            uncertainty:   0.8%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoWriteBench score: 685.817810 (685.8 653.1%)
 *            uncertainty:   5.0%
 */
/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoWriteBench score: 671.841064 (671.8 651.0%)
 *            uncertainty:   0.8%
 */
public final class KryoWriteBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		String book = "";
		try {
			book = new String(Files.readAllBytes(Paths.get("res/bible_only_words.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String[] words = StringKit.split(book, " ");
		ObjectSet<String> unique = ObjectSet.with(words);
		HashMap<String, ArrayList<Vector2>> big = new HashMap<>(unique.size);
		FourWheelRandom random = new FourWheelRandom(12345);
		for(String u : unique){
			big.put(u, new ArrayList<>(Arrays.asList(
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f)
			)));
		}
		Kryo kryo = new Kryo();
		kryo.register(HashMap.class);
		kryo.register(ArrayList.class);
		kryo.register(Vector2.class);

		long counter = 0;

		Output output = new Output(65536, -1);

		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				output.reset();
				startTimer();
				kryo.writeObject(output, big);
				pauseTimer();
				counter += output.total();
			}
		}
		return numLoops * numIterationsPerLoop;
	}

	public static void main(String[] args) {
		String book = "";
		try {
			book = new String(Files.readAllBytes(Paths.get("res/bible_only_words.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String[] words = StringKit.split(book, " ");
		OrderedSet<String> unique = OrderedSet.with(words);
		HashMap<String, ArrayList<Vector2>> big = new HashMap<>(unique.size);
		FourWheelRandom random = new FourWheelRandom(12345);
		for(String u : unique){
			big.put(u, new ArrayList<>(Arrays.asList(
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f)
			)));
		}

		System.out.println("There are " + big.size() + " keys in the Map.");

		Kryo kryo = new Kryo();
		kryo.register(HashMap.class);
		kryo.register(ArrayList.class);
		kryo.register(Vector2.class);

		try {
			Output output = new Output(new FileOutputStream("kryo.dat"));
			kryo.writeObject(output, big);
			output.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}


//OLDER SURROUNDING CODE
/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * KryoWriteBench score: 633.149719 (633.1 645.1%)
 *            uncertainty:   1.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * KryoWriteBench score: 671.979736 (672.0 651.0%)
 *            uncertainty:   1.6%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * KryoWriteBench score: 660.632507 (660.6 649.3%)
 *            uncertainty:   8.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * KryoWriteBench score: 729.367371 (729.4 659.2%)
 *            uncertainty:   3.3%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoWriteBench score: 689.462891 (689.5 653.6%)
 *            uncertainty:   8.0%
 */

// OLDEST EVERYTHING
/*
 * Java 17:
 * <br>
 * KryoWriteBench score: 450.081726 (450.1 610.9%)
 *            uncertainty:   1.2%
 */
