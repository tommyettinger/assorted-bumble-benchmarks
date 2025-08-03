
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

import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * ThrushRandomBench score: 1563794560.000000 (1.564G 2117.0%)
 *               uncertainty:   0.6%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * ThrushRandomBench score: 1802055168.000000 (1.802G 2131.2%)
 *               uncertainty:   2.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * ThrushRandomBench score: 1818241024.000000 (1.818G 2132.1%)
 *               uncertainty:   0.7%
 * <br>
 * GraalVM Java 24:
 * <br>
 * ThrushRandomBench score: 1725688960.000000 (1.726G 2126.9%)
 *               uncertainty:   2.9%
 */
public final class ThrushRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		ThrushRandom rng = new ThrushRandom(0x12345678);
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
