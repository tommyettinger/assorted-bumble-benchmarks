
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
 * ThrashRandomBench score: 1872270592.000000 (1.872G 2135.0%)
 *               uncertainty:   1.0%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * ThrashRandomBench score: 2005555968.000000 (2.006G 2141.9%)
 *               uncertainty:   0.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * ThrashRandomBench score: 1998752640.000000 (1.999G 2141.6%)
 *               uncertainty:   0.3%
 * <br>
 * GraalVM Java 24:
 * <br>
 * ThrashRandomBench score: 1951312000.000000 (1.951G 2139.2%)
 *               uncertainty:   0.3%
 */
public final class ThrashRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		ThrashRandom rng = new ThrashRandom(0x12345678);
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
