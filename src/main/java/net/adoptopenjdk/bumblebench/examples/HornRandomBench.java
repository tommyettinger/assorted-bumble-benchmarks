
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

import com.github.tommyettinger.random.HornRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * HornRandomBench score: 1402391808.000000 (1.402G 2106.1%)
 *             uncertainty:   0.3%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * HornRandomBench score: 1447892224.000000 (1.448G 2109.3%)
 *             uncertainty:   4.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * HornRandomBench score: 1533186560.000000 (1.533G 2115.1%)
 *             uncertainty:   0.2%
 * <br>
 * GraalVM Java 24:
 * <br>
 * HornRandomBench score: 1564255872.000000 (1.564G 2117.1%)
 *             uncertainty:   0.2%
 */
public final class HornRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		HornRandom rng = new HornRandom(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}

// TEMPLATE
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 *
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 *
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 *
 * <br>
 * GraalVM Java 24:
 * <br>
 *
 */
