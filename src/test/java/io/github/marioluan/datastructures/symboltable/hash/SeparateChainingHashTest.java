package io.github.marioluan.datastructures.symboltable.hash;

import static com.greghaskins.spectrum.Spectrum.afterEach;
import static com.greghaskins.spectrum.Spectrum.beforeEach;
import static com.greghaskins.spectrum.Spectrum.describe;
import static com.greghaskins.spectrum.Spectrum.it;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Random;

import org.junit.runner.RunWith;
import com.greghaskins.spectrum.Spectrum;

@RunWith(Spectrum.class)
public class SeparateChainingHashTest {

    private SeparateChainingHash<String, Integer> subject;
    private static final Random                   RANDOM = new Random();
    private String                                key;
    private Integer                               value;

    {
        describe("SeparateChainingHash", () -> {
            beforeEach(() -> {
                this.subject = new SeparateChainingHash<>(1);
                this.key = String.valueOf(RANDOM.nextInt(26 + 'a'));
                this.value = RANDOM.nextInt();
            });

            afterEach(() -> {
                this.subject = null;
                this.key = null;
                this.value = null;
            });

            describe("constructor", () -> {
                it("initializes an empty symbol table", () -> {
                    assertTrue(this.subject.isEmpty());
                });
            });

            describe("#isEmpty", () -> {
                describe("when it is empty", () -> {
                    it("returns true", () -> {
                        assertTrue(this.subject.isEmpty());
                    });
                });

                describe("when it is not empty", () -> {
                    beforeEach(() -> {
                        this.subject.put(key, value);
                    });

                    it("returns false", () -> {
                        assertFalse(this.subject.isEmpty());
                    });
                });
            });

            describe("#size", () -> {
                describe("when it is empty", () -> {
                    it("returns its @size", () -> {
                        assertEquals(0, this.subject.size());
                    });
                });

                describe("when it is not empty", () -> {
                    beforeEach(() -> {
                        this.subject.put(key, value);
                    });

                    it("returns its @size", () -> {
                        assertEquals(1, this.subject.size());
                    });
                });
            });

            describe("#put", () -> {
                describe("when key is null", () -> {
                    it("throws null pointer", () -> {
                        NullPointerException throwed = null;

                        try {
                            this.subject.put(null, value);
                        } catch (NullPointerException e) {
                            throwed = e;
                        }

                        assertNotNull(throwed);
                    });
                });

                describe("when key is not null", () -> {
                    it("does not throw null pointer", () -> {
                        NullPointerException throwed = null;

                        try {
                            this.subject.put(key, value);
                        } catch (NullPointerException e) {
                            throwed = e;
                        }

                        assertNull(throwed);
                    });

                    describe("when key already exists", () -> {
                        describe("when key is at the top of the bucket", () -> {
                            describe("when value is null", () -> {
                                beforeEach(() -> {
                                    this.subject.put(key + 1, value);
                                    this.subject.put(key, value);
                                    this.subject.put(key, null);
                                });

                                it("removes the key from table", () -> {
                                    assertFalse(this.subject.contains(key));
                                });

                                it("decrements size by 1", () -> {
                                    assertEquals(1, this.subject.size());
                                });
                            });

                            describe("when value is not null", () -> {
                                beforeEach(() -> {
                                    this.subject.put(key + 1, value);
                                    this.subject.put(key, RANDOM.nextInt());
                                    this.subject.put(key, value);
                                });

                                it("updates the key from table", () -> {
                                    assertEquals(this.subject.get(key), value);
                                });

                                it("does not updates its size", () -> {
                                    assertEquals(2, this.subject.size());
                                });
                            });
                        });

                        describe("when key isn't at the top of the bucket",
                                () -> {
                                    describe("when value is null", () -> {
                                        beforeEach(() -> {
                                            this.subject.put("k", value);
                                            this.subject.put("a", value);
                                            this.subject.put("a", null);
                                        });

                                        it("removes the key from table", () -> {
                                            assertFalse(
                                                    this.subject.contains("a"));
                                        });

                                        it("decrements size by 1", () -> {
                                            assertEquals(1,
                                                    this.subject.size());
                                        });
                                    });

                                    describe("when value is not null", () -> {
                                        beforeEach(() -> {
                                            this.subject.put("k", value);
                                            this.subject.put("a",
                                                    RANDOM.nextInt());
                                            this.subject.put("a", value);
                                        });

                                        it("updates the key from table", () -> {
                                            assertEquals(this.subject.get("a"),
                                                    value);
                                        });

                                        it("does not updates its size", () -> {
                                            assertEquals(2,
                                                    this.subject.size());
                                        });
                                    });
                                });
                    });

                    describe("when key does not exist yet", () -> {
                        describe("when value is null", () -> {
                            it("does not add the key to table", () -> {
                                this.subject.put(key, null);

                                assertFalse(this.subject.contains(key));
                                assertEquals(0, this.subject.size());
                            });
                        });

                        describe("when value is not null", () -> {
                            beforeEach(() -> {
                                this.subject.put(key + 1, value);
                                this.subject.put(key, value);
                            });

                            it("adds the key to table", () -> {
                                assertTrue(this.subject.contains(key));
                                assertEquals(this.subject.get(key), value);
                            });

                            it("increments size by 1", () -> {
                                assertEquals(2, this.subject.size());
                            });
                        });
                    });
                });
            });

            describe("#get", () -> {
                describe("when key is null", () -> {
                    it("throws null pointer", () -> {
                        NullPointerException throwed = null;

                        try {
                            this.subject.get(null);
                        } catch (NullPointerException e) {
                            throwed = e;
                        }

                        assertNotNull(throwed);
                    });
                });

                describe("when key is not null", () -> {
                    it("does not throw null pointer", () -> {
                        NullPointerException throwed = null;

                        try {
                            this.subject.get(key);
                        } catch (NullPointerException e) {
                            throwed = e;
                        }

                        assertNull(throwed);
                    });

                    describe("when table is empty", () -> {
                        it("returns null", () -> {
                            assertNull(this.subject.get(key));
                        });
                    });

                    describe("when table is not empty", () -> {
                        describe("when table has only a single node", () -> {
                            beforeEach(() -> {
                                this.subject.put(key, value);
                            });

                            describe("when key exists", () -> {
                                it("returns the value paired with it", () -> {
                                    assertEquals(value, this.subject.get(key));
                                });
                            });

                            describe("when key does not exist", () -> {
                                it("returns null", () -> {
                                    assertNull(this.subject.get(
                                            String.valueOf(RANDOM.nextInt())));
                                });
                            });
                        });

                        describe("when table has multiple nodes", () -> {
                            beforeEach(() -> {
                                this.subject.put(key, value);
                                this.subject.put(key + 1, value + 1);
                                this.subject.put(key + 2, value + 2);
                            });

                            describe("when key exists", () -> {
                                it("returns the value paired with it", () -> {
                                    assertEquals(value, this.subject.get(key));
                                });
                            });

                            describe("when key does not exist", () -> {
                                it("returns null", () -> {
                                    assertNull(this.subject.get(
                                            String.valueOf(RANDOM.nextInt())));
                                });
                            });
                        });
                    });
                });
            });

            describe("#delete", () -> {
                describe("when key is at the top of the bucket", () -> {
                    it("removes the key from table", () -> {
                        subject.put(key, value);

                        assertTrue(subject.contains(key));

                        subject.delete(key);

                        assertFalse(subject.contains(key));
                    });
                });

                describe("when key is at the middle/bottom of the bucket",
                        () -> {
                            it("removes the key from table", () -> {
                                subject.put("k", value);
                                subject.put("a", value);
                                subject.put("u", value);
                                subject.put("b", value);
                                subject.put("c", value);
                                subject.put("j", value);

                                assertTrue(subject.contains("a"));

                                subject.delete("a");
                                assertFalse(subject.contains("a"));

                                assertTrue(subject.contains("k"));
                                assertTrue(subject.contains("u"));
                            });
                        });
            });

            describe("#keys", () -> {
                describe("when table is empty", () -> {
                    it("returns an empty iterator", () -> {
                        assertNotNull(subject.keys());
                        assertFalse(subject.keys().iterator().hasNext());
                    });
                });

                describe("when table is not empty", () -> {
                    describe("when the buckets have a single element", () -> {
                        it("returns the keys from the table", () -> {
                            String[] keys = { key, key + 1, key + 2 };
                            this.subject.put(keys[0], value);
                            this.subject.put(keys[1], value + 1);
                            this.subject.put(keys[2], value + 2);

                            int size = 0;
                            int expected = 3;

                            for (String k : this.subject.keys()) {
                                if (!k.equals(keys[0]) && !k.equals(keys[1])
                                        && !k.equals(keys[2]))
                                    assertFalse(true);

                                size++;
                            }

                            assertEquals(expected, size);

                        });
                    });

                    describe("when the buckets have multiple elements", () -> {
                        it("returns the keys from the table", () -> {
                            String[] keys = { "a", "k", "u" };
                            this.subject.put(keys[0], value);
                            this.subject.put(keys[1], value + 1);
                            this.subject.put(keys[2], value + 2);

                            int size = 0;
                            int expected = 3;

                            for (String k : this.subject.keys()) {
                                if (!k.equals(keys[0]) && !k.equals(keys[1])
                                        && !k.equals(keys[2]))
                                    assertFalse(true);

                                size++;
                            }

                            assertEquals(expected, size);

                        });
                    });
                });
            });
        });
    }
}
