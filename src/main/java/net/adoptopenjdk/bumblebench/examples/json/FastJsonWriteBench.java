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

package net.adoptopenjdk.bumblebench.examples.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedSet;
import com.github.tommyettinger.random.FourWheelRandom;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import squidpony.StringKit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Java 17:
 * <br>
 * FastJsonWriteBench score: 47.425140 (47.43 385.9%)
 *                uncertainty:   3.8%
 * <br>
 * HotSpot Java 24 (BellSoft):
 * <br>
 * FastJsonWriteBench score: 215.896835 (215.9 537.5%)
 *                uncertainty:   3.9%
 * <br>
 * (same as above, but with FieldBased enabled)
 * <br>
 * FastJsonWriteBench score: 269.577728 (269.6 559.7%)
 *                uncertainty:   4.1%
 */
public final class FastJsonWriteBench extends MiniBench {
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
		JSON.config(JSONWriter.Feature.FieldBased, true);
		JSON.config(JSONReader.Feature.FieldBased, true);
		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				counter += JSON.toJSONString(big).length();
				pauseTimer();
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
		JSON.config(JSONWriter.Feature.FieldBased, true);
		JSON.config(JSONReader.Feature.FieldBased, true);

		new HeadlessFiles().local("fastjson.json").writeString(JSON.toJSONString(big), false);
	}

}

