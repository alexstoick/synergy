package synergy.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import synergy.database.RelationshipDao;
import synergy.database.TagDao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexstoick on 2/6/15.
 */
@DatabaseTable(tableName = "tags")
public class Tag {
	@DatabaseField(generatedId = true, columnName = _ID)
	private int ID;
	@DatabaseField(columnName = COLUMN_TYPE, uniqueCombo = true)
	private TagType type;

	@DatabaseField(columnName = COLUMN_VALUE, uniqueCombo = true)
	private String value;

	public static final String _ID = "id";
	public static final String COLUMN_TYPE = "tag_type";
	public static final String COLUMN_VALUE = "value";

	public enum TagType {
		    KID, PLACE, EXTRA
	}

	public Tag(){
	}

	public Tag (TagType type, String value) {
		this.type = type;
		this.value = value;
	}

	public void save() {
		try {
			TagDao.getInstance ().createOrUpdate (this);
		} catch ( Exception e ) {
			System.err.println (e);
			e.printStackTrace ();
		}
	}
    public static List<Tag> getAllPlacesTags(){
        try {
            return TagDao.getInstance ().getAllPlacesTags();
        } catch ( SQLException e) {
            System.err.println(e);
            e.printStackTrace ();
        }
        return null;
    }
	public static List<Tag> getAllChildrenTags() {
		try {
			return TagDao.getInstance ().getAllChildrenTags();
		} catch ( SQLException e) {
			System.err.println(e);
			e.printStackTrace ();
		}
		return null;
	}

	public void setID (int ID) {
		this.ID = ID;
	}

	public TagType getType () {
		return type;
	}

	public String getValue () {
		return value;
	}

	public int getID () {
		return ID;
	}

	public static List<Photo> getPhotosForTag(Tag tag) {
		tag.save();
		try {
			return TagDao.getInstance ().getPhotosForTag(tag);
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace ();
		}
		return null;
	}

	public static List<Tag> getSuggestedTagsForString(String text) {
		try {
			return TagDao.getInstance ().tagWithValueLike (text);
		} catch(SQLException e) {
			System.err.println(e);
			e.printStackTrace ();
		}
		return null;
	}

    public List<Relationship> getRelationshipsForTagSortedByOccurrences(){
        try {
            return RelationshipDao.getInstance().getRelationshipsForTagSortedByOccurrences(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public String toString () {
		return "\nTag{" +
				"ID=" + ID +
				", type=" + type +
				", value='" + value + '\'' +
				"}\n";
	}

	@Override
	public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        return ID == tag.ID && type == tag.type && value.equals(tag.value);

    }


}
