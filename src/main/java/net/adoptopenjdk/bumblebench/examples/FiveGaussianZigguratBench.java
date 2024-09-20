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

import com.github.tommyettinger.random.Ziggurat;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianZigguratBench score: 64376392.000000 (64.38M 1798.0%)
 *                       uncertainty:   0.8%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianZigguratBench score: 61688492.000000 (61.69M 1793.8%)
 *                       uncertainty:   1.0%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianZigguratBench score: 60904660.000000 (60.90M 1792.5%)
 *                       uncertainty:   1.1%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianZigguratBench score: 98396968.000000 (98.40M 1840.5%)
 *                       uncertainty:   0.3%
 */
public final class FiveGaussianZigguratBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		PouchRandom rng = new PouchRandom(0x12345678);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += Ziggurat.normal(rng.nextLong());
			sum += Ziggurat.normal(rng.nextLong());
			sum += Ziggurat.normal(rng.nextLong());
			sum += Ziggurat.normal(rng.nextLong());
			sum += Ziggurat.normal(rng.nextLong());
        }
		return numIterations;
	}
}
