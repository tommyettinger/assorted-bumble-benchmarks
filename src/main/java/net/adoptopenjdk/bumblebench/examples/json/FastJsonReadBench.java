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
import com.alibaba.fastjson2.TypeReference;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.math.Vector2;
import com.github.tommyettinger.ds.ObjectObjectMap;
import net.adoptopenjdk.bumblebench.core.MiniBench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Wow, that's bad. This treats the "isUnit()" and "isZero()" predicates as getters for non-existent properties.
 * We can disable that by setting the FieldBased Feature to true, though. This is indeed fast.
 * Java 17:
 * <br>
 * FastJsonReadBench score: 57.170677 (57.17 404.6%)
 *               uncertainty:   2.6%
 * <br>
 * HotSpot Java 24 (BellSoft), FastJson2:
 * <br>
 * FastJsonReadBench score: 171.888123 (171.9 514.7%)
 *               uncertainty:   0.5%
 * <br>
 * (same as above, but with FieldBased enabled)
 * <br>
 * FastJsonReadBench score: 211.745392 (211.7 535.5%)
 *               uncertainty:   1.1%
 */
public final class FastJsonReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		String data = new HeadlessFiles().local("fastjson.json").readString();
		HashMap<String, ArrayList<Vector2>> big;

		JSON.config(JSONWriter.Feature.FieldBased, true);
		JSON.config(JSONReader.Feature.FieldBased, true);
		TypeReference<HashMap<String, ArrayList<Vector2>>> type = new TypeReference<HashMap<String, ArrayList<Vector2>>>() {};

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				big = JSON.parseObject(data, type);
				counter += big.size();
				pauseTimer();
			}
		}
		return numLoops * numIterationsPerLoop;
	}

	public static void main(String[] args) {
		ArrayList<ArrayList<HashMap<Vector2, String>>> deep = new ArrayList<>(8), after;
		HashMap<Vector2, String> hm0 = new HashMap<>(1);
		HashMap<Vector2, String> hm1 = new HashMap<>(ObjectObjectMap.with(new Vector2(1, 2), "1 2"));
		HashMap<Vector2, String> hm2 = new HashMap<>(ObjectObjectMap.with(new Vector2(3, 4), "3 4", new Vector2(5, 6), "5 6"));
		HashMap<Vector2, String> hm3 = new HashMap<>(ObjectObjectMap.with(new Vector2(7, 8), "7 8", new Vector2(9, 0), "9 0"));
		deep.add(new ArrayList<>(Arrays.asList(hm0, hm1)));
		deep.add(new ArrayList<>(Arrays.asList(hm2, hm3)));
		deep.add(new ArrayList<>(Arrays.asList(hm0, hm1, hm2, hm3)));
		JSON.config(JSONWriter.Feature.FieldBased, true);
		JSON.config(JSONReader.Feature.FieldBased, true);
		TypeReference<ArrayList<ArrayList<HashMap<Vector2, String>>>> type = new TypeReference<ArrayList<ArrayList<HashMap<Vector2, String>>>>() {};
		String data = JSON.toJSONString(deep);
		System.out.println(data);
		after = JSON.parseObject(data, type);
		System.out.println(after);
	}
}

