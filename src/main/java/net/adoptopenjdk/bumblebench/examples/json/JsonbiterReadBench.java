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
import com.github.tommyettinger.jsonbiter.JsonIterator;
import com.github.tommyettinger.jsonbiter.spi.Config;
import com.github.tommyettinger.jsonbiter.spi.DecodingMode;
import com.github.tommyettinger.jsonbiter.spi.TypeLiteral;
import net.adoptopenjdk.bumblebench.core.MiniBench;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * HotSpot Java 8 (BellSoft):
 * <br>
 * JsonbiterReadBench score: 242.601471 (242.6 549.1%)
 *                uncertainty:   1.0%
 * <br>
 * HotSpot Java 26 (Azul):
 * <br>
 * JsonbiterReadBench score: 273.089325 (273.1 561.0%)
 *                uncertainty:   2.3%
 */
public final class JsonbiterReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		String data = new HeadlessFiles().local("jsonbiter.json").readString();
		HashMap<String, ArrayList<Vector2>> big;
		Config cfg = new Config.Builder()
				.omitDefaultValue(true)
				.decodingMode(DecodingMode.REFLECTION_MODE)
				.build();
		TypeLiteral<HashMap<String, ArrayList<Vector2>>> tl = new TypeLiteral<HashMap<String, ArrayList<Vector2>>>(){};
		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				big = JsonIterator.deserialize(cfg, data, tl);
				counter += big.size();
				pauseTimer();
			}
		}
		return numLoops * numIterationsPerLoop;
	}
}

