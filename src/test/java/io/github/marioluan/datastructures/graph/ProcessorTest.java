package io.github.marioluan.datastructures.graph;

import com.greghaskins.spectrum.Spectrum;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.Random;

import static com.greghaskins.spectrum.Spectrum.*;

@RunWith(Spectrum.class)
public class ProcessorTest {

    // let's keep it small so we don't spend time creating empty graphs
    private final int V_MAX_BOUND = 10;
    private int V;
    private Graph graph;

    {
        describe("Processor", () -> {
            beforeEach(() -> {
                V = new Random().nextInt(V_MAX_BOUND);
                V = Math.abs(V);
                graph = new Undirected(V);
            });

            afterEach(() -> {
                V = 0;
                graph = null;
            });

            describe(".degree", () -> {
                describe("with vertex v", () -> {
                    describe("when it is within graph G", () -> {
                        describe("when it has adjacent vertices", () -> {
                            it("returns the number of adjacent vertices", () -> {
                                int v = 0;
                                int w = 1;

                                V = 2;
                                graph = new Undirected(V);

                                graph.addEdge(v, w);

                                Assert.assertEquals(1, Processor.degree(graph, v));
                            });
                        });

                        describe("when it doesn't have adjacent vertices", () -> {
                            it("returns zero", () -> {
                                int V = 2;
                                int v = 0;

                                Graph graph = new Undirected(V);
                                Assert.assertEquals(0, Processor.degree(graph, v));
                            });
                        });
                    });

                    describe("when it isn't within graph G", () -> {
                        it("throws ArrayIndexOutOfBoundsException", () -> {
                            boolean thrown = false;
                            int V = 2;
                            int v = 2;

                            Graph graph = new Undirected(V);

                            try {
                                Processor.degree(graph, v);
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                thrown = true;
                            }

                            Assert.assertTrue(thrown);
                        });
                    });
                });
            });

            describe(".maxDegree", () -> {
                describe("with graph G", () -> {
                    describe("when it's empty", () -> {
                        beforeEach(() -> {
                            graph = new Undirected(V);
                        });

                        it("returns zero", () -> {
                            Assert.assertEquals(0, Processor.maxDegree(graph));
                        });
                    });

                    describe("when it isn't empty", () -> {
                        beforeEach(() -> {
                            V = 4;
                            graph = new Undirected(V);
                            graph.addEdge(0, 1);
                            graph.addEdge(0, 2);
                            graph.addEdge(0, 3);
                            graph.addEdge(1, 2);
                            graph.addEdge(1, 3);
                            graph.addEdge(2, 3);
                        });

                        it("returns the maximum degree found", () -> {
                            int expected = 3;

                            Assert.assertEquals(expected, Processor.maxDegree(graph));
                        });
                    });
                });
            });

            describe(".averageDegree", () -> {
                describe("with graph G", () -> {
                    describe("when it's empty", () -> {
                        beforeEach(() -> {
                            graph = new Undirected(V);
                        });

                        it("returns zero", () -> {
                            Assert.assertEquals(0, Processor.averageDegree(graph), 1);
                        });
                    });

                    describe("when it isn't empty", () -> {
                        beforeEach(() -> {
                            V = 4;
                            graph = new Undirected(V);
                            graph.addEdge(0, 1);
                            graph.addEdge(0, 2);
                            graph.addEdge(0, 3);
                            graph.addEdge(1, 2);
                            graph.addEdge(1, 3);
                            graph.addEdge(2, 3);
                        });

                        it("returns the average degree", () -> {
                            double expected = 2.0 * graph.E() / graph.V();

                            Assert.assertEquals(expected, Processor.averageDegree(graph), 1);
                        });
                    });
                });
            });

            describe(".numberOfSelfLoops", () -> {
                describe("with graph G", () -> {
                    describe("when it's empty", () -> {
                        beforeEach(() -> {
                            graph = new Undirected(V);
                        });

                        it("returns zero", () -> {
                            Assert.assertEquals(0, Processor.numberOfSelfLoops(graph));
                        });
                    });

                    describe("when it isn't empty", () -> {
                        beforeEach(() -> {
                            V = 2;
                            graph = new Undirected(V);
                        });

                        describe("when there are self-loops", () -> {
                            beforeEach(() -> {
                                graph.addEdge(0, 0);
                                graph.addEdge(0, 1);
                                graph.addEdge(1, 1);
                            });

                            it("returns the number of self-loops", () -> {
                                Assert.assertEquals(2, Processor.numberOfSelfLoops(graph));
                            });
                        });
                    });
                });
            });
        });
    }
}


