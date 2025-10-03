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
 * RandomizeMixBench score: 1177573120.000000 (1.178G 2088.7%)
 *               uncertainty:   0.3%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * RandomizeMixBench score: 1313957120.000000 (1.314G 2099.6%)
 *               uncertainty:   1.2%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * RandomizeMixBench score: 1332282752.000000 (1.332G 2101.0%)
 *               uncertainty:   0.2%
 * <br>
 * GraalVM Java 24:
 * <br>
 * RandomizeMixBench score: 1126424192.000000 (1.126G 2084.2%)
 *               uncertainty:   0.1%
 */
public final class RandomizeMixBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += Hasher.mix(i);
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
