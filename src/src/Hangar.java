import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Hangar <T extends ITransport, V extends IBombs>{
	private Map<Integer,T> _places;
	private int pictureWidth;
	private int pictureHeight;
	private int _maxCount;
	private final int _placeSizeWidth = 210;
	private final int _placeSizeHeight = 90;
	private Color _color;
	
	public Hangar(int sizes, int pictureWidth, int pictureHeight)
    {
		_maxCount = sizes;
		_places = new LinkedHashMap <Integer,T>();
        this.pictureWidth = pictureHeight;
        this.pictureHeight = pictureHeight;
    }
	
	public T GetTransport(int indexTransport){
		return _places.get(indexTransport);
	}
    
	public int operatorAdd(T warPlane) throws Exception {
		if (_places.size() == _maxCount)
			throw new Exception();
		for (int i = 0; i < _maxCount; i++)
			if (CheckFreePlace(i)) 
			{
				warPlane.SetPosition(5 + i / 5 * _placeSizeWidth + 5, i % 5 * _placeSizeHeight + 15, pictureWidth,
						pictureHeight);
				_places.put(i, warPlane);
				return i;
			}
		throw new Exception();
	}

	public T operatorSub(int index) throws Exception
    {
		 if (!CheckFreePlace(index))
         {
             T warPlane = _places.get(index);
             _places.remove(index);
             return warPlane;
         }
		 throw new Exception();
    }
	
	public int operatorMul(int index)
    {
        if (!CheckFreePlace(index))
        {
            T warPlane = _places.get(index);
            if (!(warPlane instanceof Bomber))
            {
				int typeBombs = new Random().nextInt(3);
				IBombs bombs;
				if (typeBombs == 0)
					bombs = new CircleBombs();
				else if (typeBombs == 1)
					bombs = new SquareBombs();
				else
					bombs = new RectangleBombs();
				bombs.SetPosition(5 + index / 5 * _placeSizeWidth + 5, index % 5 * _placeSizeHeight + 15);
				T bomber = (T) new Bomber(((WarPlane)warPlane).GetMaxSpeed(), ((WarPlane)warPlane).GetWeight(), ((WarPlane)warPlane).GetMainColor(), 
                		_color, true, true, true, bombs);
				bomber.SetPosition(5 + index / 5 * _placeSizeWidth + 5, index % 5 * _placeSizeHeight + 15, pictureWidth, pictureHeight);
				_places.put(index, bomber);
                return index;
            }
        }
        return -1;
    }

	public int operatorDiv(int index)
    {
        if (!CheckFreePlace(index))
        {
            T warPlane = _places.get(index);
            if (warPlane instanceof Bomber)
            {
            	WarPlane plane = (WarPlane)warPlane;
            	T wPlane = (T) new WarPlane(plane.GetMaxSpeed(), plane.GetWeight(), plane.GetMainColor());
            	wPlane.SetPosition(5 + index / 5 * _placeSizeWidth + 5, index % 5 * _placeSizeHeight + 15, pictureWidth, pictureHeight);
            	_places.put(index, wPlane);
                return index;
            }
        }
        return -1;
    }

    private boolean CheckFreePlace(int index)
    {
    	return !_places.containsKey(index);
    }

    public void Draw(Graphics g)
    {
    	DrawMarking(g);
    	Object[] keys = _places.keySet().toArray();
        for (int i = 0; i < keys.length; i++)
            _places.get((int)keys[i]).DrawWarPlane(g);
    }

    private void DrawMarking(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(0, 0, (_maxCount / 5) * _placeSizeWidth, 450);
        for (int i = 0; i < _maxCount / 5; i++)
        {
            for (int j = 0; j < 5; ++j)
                g.drawLine(i * _placeSizeWidth, j * _placeSizeHeight, i * _placeSizeWidth + 110, j * _placeSizeHeight);
            g.drawLine(i * _placeSizeWidth, 0, i * _placeSizeWidth, 450);
        }
    }
    
    public void SetColorChangeWarPlane(int index, Color color)
    {
        if (!CheckFreePlace(index))
            if (!(_places.get(index) instanceof Bomber))
            	_color = color;
    }
    
    public T GetHangar(int ind)
    {
    	if (_places.containsKey(ind))
            return _places.get(ind);
        return null;
    }
    
    public void AddWarPlane(int ind, T value)
    {
		if (CheckFreePlace(ind)) {
			_places.put(ind, value);
			_places.get(ind).SetPosition(5 + ind / 5 * _placeSizeWidth + 5, ind % 5 * _placeSizeHeight + 15,
					pictureWidth, pictureHeight);
		}
    }
}