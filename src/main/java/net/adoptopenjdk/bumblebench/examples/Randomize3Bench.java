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
 * Randomize3Bench score: 1105558528.000000 (1.106G 2082.4%)
 *             uncertainty:   0.7%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Randomize3Bench score: 1126536576.000000 (1.127G 2084.2%)
 *             uncertainty:   0.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Randomize3Bench score: 1124957440.000000 (1.125G 2084.1%)
 *             uncertainty:   0.8%
 * <br>
 * GraalVM Java 24:
 * <br>
 * Randomize3Bench score: 1351691648.000000 (1.352G 2102.5%)
 *             uncertainty:   1.4%
 */
public final class Randomize3Bench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += Hasher.randomize3(i);
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
