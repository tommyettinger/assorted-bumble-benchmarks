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

import com.github.tommyettinger.digital.Hasher;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * RandomizeHBench score: 1184455424.000000 (1.184G 2089.3%)
 *             uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * RandomizeHBench score: 1212853760.000000 (1.213G 2091.6%)
 *             uncertainty:   0.1%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * RandomizeHBench score: 1221176064.000000 (1.221G 2092.3%)
 *             uncertainty:   1.7%
 * <br>
 * GraalVM Java 24:
 * <br>
 * RandomizeHBench score: 1234104320.000000 (1.234G 2093.4%)
 *             uncertainty:   0.1%
 */
public final class RandomizeHBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += Hasher.randomizeH(i);
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
