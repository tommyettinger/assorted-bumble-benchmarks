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

import com.github.tommyettinger.digital.TrigTools;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianFastBoxMullerBench score: 8553293.000000 (8.553M 1596.2%)
 *                            uncertainty:   3.1%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianFastBoxMullerBench score: 23529746.000000 (23.53M 1697.4%)
 *                            uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianFastBoxMullerBench score: 23674174.000000 (23.67M 1698.0%)
 *                            uncertainty:   1.1%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianFastBoxMullerBench score: 31836892.000000 (31.84M 1727.6%)
 *                            uncertainty:   1.8%
 */
public final class FiveGaussianFastBoxMullerBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		PouchRandom rng = new PouchRandom(0x12345678);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			long n;
			n = rng.nextLong(); sum += Math.sqrt(-2 * Math.log(((n >>> 11) + 1L) * 0x1p-53)) * TrigTools.COS_TABLE_D[(int)n & TrigTools.TABLE_MASK];
			n = rng.nextLong(); sum += Math.sqrt(-2 * Math.log(((n >>> 11) + 1L) * 0x1p-53)) * TrigTools.COS_TABLE_D[(int)n & TrigTools.TABLE_MASK];
			n = rng.nextLong(); sum += Math.sqrt(-2 * Math.log(((n >>> 11) + 1L) * 0x1p-53)) * TrigTools.COS_TABLE_D[(int)n & TrigTools.TABLE_MASK];
			n = rng.nextLong(); sum += Math.sqrt(-2 * Math.log(((n >>> 11) + 1L) * 0x1p-53)) * TrigTools.COS_TABLE_D[(int)n & TrigTools.TABLE_MASK];
			n = rng.nextLong(); sum += Math.sqrt(-2 * Math.log(((n >>> 11) + 1L) * 0x1p-53)) * TrigTools.COS_TABLE_D[(int)n & TrigTools.TABLE_MASK];
		}
		return numIterations;
	}
}
