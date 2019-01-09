package com.jiyun_z5.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.jiyun_z5.bean.BannerItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BANNER_ITEM".
*/
public class BannerItemDao extends AbstractDao<BannerItem, Long> {

    public static final String TABLENAME = "BANNER_ITEM";

    /**
     * Properties of entity BannerItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Desc = new Property(0, String.class, "desc", false, "DESC");
        public final static Property Id = new Property(1, long.class, "id", true, "_id");
        public final static Property ImagePath = new Property(2, String.class, "imagePath", false, "IMAGE_PATH");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property Url = new Property(4, String.class, "url", false, "URL");
    }


    public BannerItemDao(DaoConfig config) {
        super(config);
    }
    
    public BannerItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BANNER_ITEM\" (" + //
                "\"DESC\" TEXT," + // 0: desc
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 1: id
                "\"IMAGE_PATH\" TEXT," + // 2: imagePath
                "\"TITLE\" TEXT," + // 3: title
                "\"URL\" TEXT);"); // 4: url
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BANNER_ITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BannerItem entity) {
        stmt.clearBindings();
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(1, desc);
        }
        stmt.bindLong(2, entity.getId());
 
        String imagePath = entity.getImagePath();
        if (imagePath != null) {
            stmt.bindString(3, imagePath);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BannerItem entity) {
        stmt.clearBindings();
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(1, desc);
        }
        stmt.bindLong(2, entity.getId());
 
        String imagePath = entity.getImagePath();
        if (imagePath != null) {
            stmt.bindString(3, imagePath);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 1);
    }    

    @Override
    public BannerItem readEntity(Cursor cursor, int offset) {
        BannerItem entity = new BannerItem( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // desc
            cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // imagePath
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // url
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BannerItem entity, int offset) {
        entity.setDesc(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setId(cursor.getLong(offset + 1));
        entity.setImagePath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BannerItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BannerItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BannerItem entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
