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
 * JsoniterStrictReadBench score: 281.784149 (281.8 564.1%)
 *                     uncertainty:   0.9%
 * <br>
 * TRASH RESULTS; these were actually running some mode other than the one that should be tested.
 * <br>
 * HotSpot Java 26 (Azul):
 * <br>
 * JsoniterStrictReadBench score: 282.440338 (282.4 564.3%)
 *                     uncertainty:   5.7%
 */
public final class JsoniterStrictReadBench extends MiniBench {
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
				.decodingMode(DecodingMode.DYNAMIC_MODE_AND_MATCH_FIELD_STRICTLY)
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

