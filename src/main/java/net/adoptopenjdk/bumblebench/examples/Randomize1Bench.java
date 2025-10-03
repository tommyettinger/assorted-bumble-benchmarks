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
 * Randomize1Bench score: 1523483904.000000 (1.523G 2114.4%)
 *             uncertainty:   0.1%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Randomize1Bench score: 1556861184.000000 (1.557G 2116.6%)
 *             uncertainty:   0.2%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Randomize1Bench score: 1501241216.000000 (1.501G 2113.0%)
 *             uncertainty:   0.2%
 * <br>
 * GraalVM Java 24:
 * <br>
 * Randomize1Bench score: 1569202688.000000 (1.569G 2117.4%)
 *             uncertainty:   0.7%
 */
public final class Randomize1Bench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += Hasher.randomize1(i);
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
