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

import com.github.tommyettinger.random.GoldenQuasiRandom;
import com.github.tommyettinger.random.Ziggurat;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianBorgBench score: 83215520.000000 (83.22M 1823.7%)
 *                   uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianBorgBench score: 81063208.000000 (81.06M 1821.1%)
 *                   uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianBorgBench score: 65646784.000000 (65.65M 1800.0%)
 *                   uncertainty:   0.8%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianBorgBench score: 75216944.000000 (75.22M 1813.6%)
 *                   uncertainty:   0.7%
 */
// OLD, uses PouchRandom
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianBorgBench score: 71525608.000000 (71.53M 1808.6%)
 *                   uncertainty:   0.6%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianBorgBench score: 77016440.000000 (77.02M 1816.0%)
 *                   uncertainty:   1.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianBorgBench score: 73492784.000000 (73.49M 1811.3%)
 *                   uncertainty:   1.6%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianBorgBench score: 66492476.000000 (66.49M 1801.3%)
 *                   uncertainty:   1.1%
 */
public final class FiveGaussianBorgBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		GoldenQuasiRandom rng = new GoldenQuasiRandom(0x12345678L);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += Borg.normal(rng.nextLong());
			sum += Borg.normal(rng.nextLong());
			sum += Borg.normal(rng.nextLong());
			sum += Borg.normal(rng.nextLong());
			sum += Borg.normal(rng.nextLong());
        }
		return numIterations;
	}
}
