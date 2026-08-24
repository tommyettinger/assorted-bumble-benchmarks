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

import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.math.Vector2;
import com.github.tommyettinger.ds.ObjectObjectMap;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import org.apache.fory.json.ForyJson;
import org.apache.fory.reflect.TypeRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Like FastJson, this treats the "isUnit()" and "isZero()" predicates as getters for non-existent properties.
 * Also like FastJson, that can be disabled! Here it uses withFieldMode(true) .
 * <br>
 * HotSpot Java 24 (BellSoft), ForyJson 1.6.1:
 * <br>
 * ForyJsonReadBench score: 232.488068 (232.5 544.9%)
 *               uncertainty:   0.3%
 * <br>
 * (same as above, but with FieldMode enabled)
 * <br>
 * ForyJsonReadBench score: 285.871948 (285.9 565.6%)
 *               uncertainty:   0.2%
 */
public final class ForyJsonReadBench extends MiniBench {
	private static final ForyJson JSON = ForyJson.builder().withFieldMode(true).build();

	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		String data = new HeadlessFiles().local("foryjson.json").readString();
		HashMap<String, ArrayList<Vector2>> big;
		TypeRef<HashMap<String, ArrayList<Vector2>>> type = new TypeRef<HashMap<String, ArrayList<Vector2>>>(){};

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				big = JSON.fromJson(data, type);
				counter += big.size();
				pauseTimer();
			}
		}
		return numLoops * numIterationsPerLoop;
	}

	public static void main(String[] args) {
		ArrayList<ArrayList<HashMap<String, Vector2>>> deep = new ArrayList<>(8), after;
		HashMap<String, Vector2> hm0 = new HashMap<>(1);
		HashMap<String, Vector2> hm1 = new HashMap<>(ObjectObjectMap.with("1 2", new Vector2(1, 2)));
		HashMap<String, Vector2> hm2 = new HashMap<>(ObjectObjectMap.with("3 4", new Vector2(3, 4), "5 6", new Vector2(5, 6)));
		HashMap<String, Vector2> hm3 = new HashMap<>(ObjectObjectMap.with("7 8", new Vector2(7, 8), "9 0", new Vector2(9, 0)));
		deep.add(new ArrayList<>(Arrays.asList(hm0, hm1)));
		deep.add(new ArrayList<>(Arrays.asList(hm2, hm3)));
		deep.add(new ArrayList<>(Arrays.asList(hm0, hm1, hm2, hm3)));

		TypeRef<ArrayList<ArrayList<HashMap<String, Vector2>>>> type = new TypeRef<ArrayList<ArrayList<HashMap<String, Vector2>>>>(){};

		String data = JSON.toJson(deep, type);
		System.out.println(data);
		after = JSON.fromJson(data, type);
		System.out.println(after);
	}
}

