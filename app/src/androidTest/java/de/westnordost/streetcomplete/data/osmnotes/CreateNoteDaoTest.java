package de.westnordost.streetcomplete.data.osmnotes;

import de.westnordost.streetcomplete.data.ApplicationDbTestCase;
import de.westnordost.osmapi.map.data.BoundingBox;
import de.westnordost.osmapi.map.data.Element;
import de.westnordost.osmapi.map.data.OsmLatLon;

public class CreateNoteDaoTest extends ApplicationDbTestCase
{
	private CreateNoteDao dao;

	@Override public void setUp()
	{
		super.setUp();
		dao = new CreateNoteDao(dbHelper);
	}

	public void testAddGetDelete()
	{
		CreateNote note = new CreateNote();
		note.text = "text";
		note.position = new OsmLatLon(3,5);
		note.elementId = 123L;
		note.elementType = Element.Type.NODE;

		assertTrue(dao.add(note));
		CreateNote dbNote = dao.get(note.id);

		assertEquals(note.text, dbNote.text);
		assertEquals(note.id, dbNote.id);
		assertEquals(note.position, dbNote.position);
		assertEquals(note.elementType, dbNote.elementType);
		assertEquals(note.elementId, dbNote.elementId);

		assertTrue(dao.delete(note.id));

		assertNull(dao.get(note.id));
	}

	public void testNullable()
	{
		CreateNote note = new CreateNote();
		note.text = "text";
		note.position = new OsmLatLon(3,5);

		assertTrue(dao.add(note));
		CreateNote dbNote = dao.get(note.id);

		assertNull(dbNote.elementType);
		assertNull(dbNote.elementId);
	}

	public void testGetAll()
	{
		CreateNote note1 = new CreateNote();
		note1.text = "this is in";
		note1.position = new OsmLatLon(0.5,0.5);
		dao.add(note1);

		CreateNote note2 = new CreateNote();
		note2.text = "this is out";
		note2.position = new OsmLatLon(-0.5,0.5);
		dao.add(note2);

		assertEquals(1,dao.getAll(new BoundingBox(0,0,1,1)).size());

		assertEquals(2,dao.getAll(null).size());
	}
}
