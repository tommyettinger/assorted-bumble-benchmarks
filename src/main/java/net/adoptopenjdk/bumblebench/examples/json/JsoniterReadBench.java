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
import com.jsoniter.JsonIterator;
import com.jsoniter.spi.Config;
import com.jsoniter.spi.DecodingMode;
import com.jsoniter.spi.TypeLiteral;
import net.adoptopenjdk.bumblebench.core.MiniBench;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * HotSpot Java 8 (BellSoft):
 * <br>
 * JsoniterReadBench score: 119.849747 (119.8 478.6%)
 *               uncertainty:   0.4%
 * <br>
 * TRASH RESULTS; these were actually running some mode other than the one that should be tested.
 * <br>
 * HotSpot Java 26 (Azul):
 * <br>
 * JsoniterReadBench score: 294.025665 (294.0 568.4%)
 *               uncertainty:   4.4%
 * JsoniterReadBench score: 276.257141 (276.3 562.1%)
 *               uncertainty:   1.3%
 * JsoniterReadBench score: 280.908264 (280.9 563.8%)
 *               uncertainty:   4.5%
 */
public final class JsoniterReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		String data = new HeadlessFiles().local("jsoniter.json").readString();
		HashMap<String, ArrayList<Vector2>> big;
		Config cfg = new Config.Builder()
				.omitDefaultValue(true)
				.decodingMode(DecodingMode.DYNAMIC_MODE_AND_MATCH_FIELD_WITH_HASH)
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

