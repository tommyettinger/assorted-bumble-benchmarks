
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

import com.github.tommyettinger.random.MaceRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
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
public final class MaceRandomBench extends MicroBench {

	private long sum = 0L;
	protected long doBatch(long numIterations) throws InterruptedException {
		MaceRandom rng = new MaceRandom(0x12345678);
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}

	/**
	 * Optionally implemented by subclasses and called at the end of a run to verify
	 * whether the run was correct or not. Defaults to true. If false, an ERROR message
	 * is printed instead of the final score. Implementing methods may output their own
	 * error message(s) as well.
	 */
	@Override
	protected boolean verify() {
		System.out.println("Final sum was: ");
		System.out.println(sum);
		System.out.println();
		return super.verify();
	}
}
