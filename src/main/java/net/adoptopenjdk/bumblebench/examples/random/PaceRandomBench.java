
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

package net.adoptopenjdk.bumblebench.examples.random;

import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * (The first result in each pair uses a literal; the second uses a private static final constant.)
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * PaceRandomBench score: 1067263616.000000 (1.067G 2078.8%)
 *             uncertainty:  40.0%
 * PaceRandomBench score: 1012192832.000000 (1.012G 2073.5%)
 *             uncertainty:  14.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * PaceRandomBench score: 2107501440.000000 (2.108G 2146.9%)
 *             uncertainty:   0.4%
 * PaceRandomBench score: 2104409984.000000 (2.104G 2146.7%)
 *             uncertainty:   0.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * PaceRandomBench score: 2361616128.000000 (2.362G 2158.3%)
 *             uncertainty:   0.6%
 * PaceRandomBench score: 2360589824.000000 (2.361G 2158.2%)
 *             uncertainty:   1.0%
 * <br>
 * GraalVM CE Java 25:
 * <br>
 * PaceRandomBench score: 2761871104.000000 (2.762G 2173.9%)
 *             uncertainty:   0.2%
 * PaceRandomBench score: 2765066752.000000 (2.765G 2174.0%)
 *             uncertainty:   0.1%
 * <br>
 * HotSpot Java 27 (Zulu):
 * <br>
 * PaceRandomBench score: 2470840064.000000 (2.471G 2162.8%)
 *             uncertainty:   0.1%
 * PaceRandomBench score: 2472792320.000000 (2.473G 2162.9%)
 *             uncertainty:   0.2%
 */
public final class PaceRandomBench extends MicroBench {

	private long sum = 0L;
	protected long doBatch(long numIterations) throws InterruptedException {
		PaceRandom rng = new PaceRandom(0x12345678);
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * MaceRandomBench score: 1061079616.000000 (1.061G 2078.3%)
 *             uncertainty:  14.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * MaceRandomBench score: 2492611328.000000 (2.493G 2163.7%)
 *             uncertainty:   0.1%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * MaceRandomBench score: 2466171136.000000 (2.466G 2162.6%)
 *             uncertainty:   0.3%
 * <br>
 * GraalVM Java 24:
 * <br>
 * MaceRandomBench score: 2759608064.000000 (2.760G 2173.8%)
 *             uncertainty:   0.3%
 */
